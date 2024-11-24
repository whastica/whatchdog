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

        try {
            // Log de los detalles del evento recibido
            log.info("Evento recibido: id={}, description={}, admincId={}, updateDate={}", 
                event.getId(), event.getDescription(), event.getAdmincId(), event.getUpdateDate());
    
            // 1. Validar que el reporte tiene los detalles necesarios
            // Buscar el reporte en la base de datos usando el ID recibido
            Report report = reportRepository.findById(event.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con ID: " + event.getId()));
    
            // Verificar que los campos necesarios estén presentes en el reporte (descripción, categoría, estado, etc.)
            if (report.getDescription() == null || report.getCategoryIssue() == null || report.getStatus() == null) {
                throw new IllegalArgumentException("Faltan detalles necesarios en el reporte");
            }
    
            // 2. Actualizar el reporte con los nuevos detalles del evento
            report.setDescription(event.getDescription());
            report.setStatus(event.getStatus());
            report.setCategoryIssue(event.getCategoryIssue());
            report.setCoordinates(event.getCoordinates());
            report.setUpdateDate(event.getUpdateDate());
            report.setFotoUrl(event.getFotoUrl());
    
            // Si el evento tiene un nuevo issueId, actualizar también la relación con el Issue
            if (event.getIssueId() != null) {
                Issue issue = issueRepository.findById(event.getIssueId())
                    .orElseThrow(() -> new IllegalArgumentException("Issue no encontrado con ID: " + event.getIssueId()));
                report.setIssue(issue); // Actualizamos la relación con el Issue
            }
    
            // Guardamos el reporte actualizado en la base de datos
            reportRepository.save(report);
    
            // 3. Publicar el evento RevisedReportEvent
            RevisedReportEvent revisedReportEvent = new RevisedReportEvent(
                report.get_id(),
                report.getAdminC().getId(),  // El ID del administrador encargado de la actualización
                report.getStatus(),
                report.getCategoryIssue(),
                report.getUpdateDate(),
                report.getIssue().getId()  // O si es null, puede ser un valor predeterminado
            );
            
            // Publicamos el evento de reporte actualizado
            eventPublisher.publishRevisedReportEvent(revisedReportEvent);
    
        } catch (IllegalArgumentException e) {
            // Manejo de errores: logueamos el error y no realizamos ninguna acción adicional
            log.error("Error procesando el evento UpdateReportEvent: {}", e.getMessage());
        } catch (Exception e) {
            // Manejo genérico de excepciones, logueamos cualquier otra excepción
            log.error("Error inesperado procesando el evento UpdateReportEvent", e);
        }
    }
}