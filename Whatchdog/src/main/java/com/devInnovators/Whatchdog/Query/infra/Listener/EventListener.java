package com.devInnovators.Whatchdog.Query.infra.Listener;

import org.springframework.stereotype.Component;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.devInnovators.Whatchdog.Query.application.EventsDTO.RevisedReportEvent;

import com.devInnovators.Whatchdog.Query.application.service.QueryReportServiceImpl;



@Component
public class EventListener {

    private final QueryReportServiceImpl queryReportService;

    public EventListener(QueryReportServiceImpl queryReportService) {
        this.queryReportService = queryReportService;
    }

  //Escuchar eventos de reporte revisado
    @RabbitListener(queues ="reporteRevisadoQueue")
    public void reporteRevisado(RevisedReportEvent revisedReportEvent) {
        queryReportService.updateReportStatus(revisedReportEvent);
    }
    
}
