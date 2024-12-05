package com.devInnovators.Whatchdog.Command.infra.Listener;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.RevisedReportEvent;
import com.devInnovators.Whatchdog.Command.domain.model.AdminC;
import com.devInnovators.Whatchdog.Command.domain.model.Report;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandAdminCRepository;
import com.devInnovators.Whatchdog.Command.domain.repository.CommandReportRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




@Component
public class EventListener {

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    private final CommandReportRepository reportRepository;
    private final CommandAdminCRepository adminCRepository;

    public EventListener(CommandReportRepository reportRepository, CommandAdminCRepository adminCRepository) {
        this.reportRepository = reportRepository;
        this.adminCRepository = adminCRepository;
    }

    @RabbitListener(queues = "reporteRevisadoQueue")
    public void reporteRevisado(RevisedReportEvent event) {
        log.info("Listener activado. Evento RevisedReport recibido: {}", event);

        try {
            // Validar el evento
            validateRevisedEvent(event);

            // Procesar el reporte revisado
            Report report = processRevisedReport(event);

            log.info("Reporte actualizado exitosamente con ID: {}", report.get_id());
        } catch (IllegalArgumentException e) {
            log.error("Error procesando el evento RevisedReportEvent: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado procesando el evento RevisedReportEvent", e);
        }
    }

    private void validateRevisedEvent(RevisedReportEvent event) {
        if (event.get_id() == null || event.getStatus() == null || event.getUpdateDate() == null) {
            throw new IllegalArgumentException("El evento RevisedReportEvent no contiene todos los campos necesarios");
        }
    }

    private Report processRevisedReport(RevisedReportEvent event) {
        // Buscar el reporte en la base de datos
        Report report = reportRepository.findById(event.get_id())
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con ID: " + event.get_id()));

        // Actualizar los campos necesarios del reporte
        report.setStatus(event.getStatus());
        report.setUpdateDate(event.getUpdateDate());

        if (event.getAdmincId() != null) {
            AdminC admin = adminCRepository.findById(event.getAdmincId())
                    .orElseThrow(() -> new IllegalArgumentException("Admin no encontrado con ID: " + event.getAdmincId()));
            report.setAdminC(admin);
        }

        if (event.getCategoryIssue() != null) {
            report.setCategoryIssue(event.getCategoryIssue());
        }

        // Guardar y devolver el reporte actualizado
        return reportRepository.save(report);
    }

}
