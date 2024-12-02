package com.devInnovators.WatchdogRevisionPriorizacion.infra.Listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.RevisedReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.UpdateReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Issue;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.model.Report;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.IssueRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.domain.repository.ReportRepository;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.config.RabbitMQConfig;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.publisher.EventPublisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class EventListener {


    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    private final EventPublisher eventPublisher;
    private final ReportRepository reportRepository;
    private final IssueRepository issueRepository;

    public EventListener(EventPublisher eventPublisher, ReportRepository reportRepository, IssueRepository issueRepository) {
        this.eventPublisher = eventPublisher;
        this.reportRepository = reportRepository;
        this.issueRepository = issueRepository;
    }

    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_REPORTE_ACTUALIZADO)
    public void onReportUpdate(UpdateReportEvent event) {
        // Log inicial para verificar si el listener recibe el evento
        log.info("Listener activado. Evento recibido: {}", event);

        try {
            // Validar el evento
            validateEvent(event);

            // Obtener y actualizar el reporte
            Report report = updateReport(event);

            // Publicar el evento revisado
            publishRevisedEvent(report);

        } catch (IllegalArgumentException e) {
            log.error("Error procesando el evento UpdateReportEvent: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado procesando el evento UpdateReportEvent", e);
        }
    }

    private void validateEvent(UpdateReportEvent event) {
        if (event.getId() == null || event.getDescription() == null || event.getStatus() == null) {
            throw new IllegalArgumentException("El evento no contiene todos los campos necesarios");
        }
    }

    private Report updateReport(UpdateReportEvent event) {
        Report report = reportRepository.findById(event.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con ID: " + event.getId()));

        report.setDescription(event.getDescription());
        report.setStatus(event.getStatus());
        report.setCategoryIssue(event.getCategoryIssue());
        report.setCoordinates(event.getCoordinates());
        report.setUpdateDate(event.getUpdateDate());
        report.setFotoUrl(event.getFotoUrl());

        if (event.getIssueId() != null) {
            Issue issue = issueRepository.findById(event.getIssueId())
                    .orElseThrow(() -> new IllegalArgumentException("Issue no encontrado con ID: " + event.getIssueId()));
            report.setIssue(issue);
        }

        return reportRepository.save(report);
    }

    private void publishRevisedEvent(Report report) {
        RevisedReportEvent revisedReportEvent = new RevisedReportEvent(
                report.get_id(),
                report.getAdminC() != null ? report.getAdminC().getId() : null,
                report.getStatus(),
                report.getCategoryIssue(),
                report.getUpdateDate(),
                report.getIssue() != null ? report.getIssue().getId() : null
        );

        eventPublisher.publishRevisedReportEvent(revisedReportEvent);
        log.info("Evento RevisedReportEvent publicado: {}", revisedReportEvent);
    }
}