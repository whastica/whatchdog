package com.devInnovators.Whatchdog.Command.infra.Listener;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.devInnovators.Whatchdog.Command.aplicattion.DTO.ReportDTO;
import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.RevisedReportEvent;
import com.devInnovators.Whatchdog.Command.aplicattion.service.CommandReportServiceImpl;


@Component
public class EventListener {

    private final CommandReportServiceImpl commandReportService;

    public EventListener(CommandReportServiceImpl commandReportService) {
        this.commandReportService = commandReportService;
    }

    @RabbitListener(queues ="reporteRevisadoQueue")
    public void reporteRevisado(RevisedReportEvent revisedReportEvent) {
        try {
            System.out.println("Recibido evento ReporteRevisado: " + revisedReportEvent);
               // Log para verificar que el id llega correctamente
            System.out.println("Received idReport: " + revisedReportEvent.get_id());
           // Convertir RevisedReportEvent a ReportDTO
           ReportDTO reportDTO = convertToReportDTO(revisedReportEvent);

           // Llamar al método updateReport con los parámetros correctos
           commandReportService.updateReport(revisedReportEvent.get_id(), reportDTO);
        } catch (Exception e) {
            // Manejo de errores, como loggear o capturar el evento para investigar
            System.out.println("Error procesando el evento RevisedReportEvent: " + e);
        }
    }
     // Método para convertir RevisedReportEvent a ReportDTO
     private ReportDTO convertToReportDTO(RevisedReportEvent revisedReportEvent) {
        ReportDTO reportDTO = new ReportDTO();
        System.out.println("Converting RevisedReportEvent to ReportDTO: " + revisedReportEvent.get_id());
        reportDTO.set_id(revisedReportEvent.get_id());
        reportDTO.setIdAdminC(revisedReportEvent.getAdmincId());
        reportDTO.setStatus(revisedReportEvent.getStatus()); 
        reportDTO.setCategoryIssue(revisedReportEvent.getCategoryIssue());
        reportDTO.setUpdateDate(revisedReportEvent.getUpdateDate());
        reportDTO.setIdissue(revisedReportEvent.getIssueId());

        return reportDTO;
    }
    
}
