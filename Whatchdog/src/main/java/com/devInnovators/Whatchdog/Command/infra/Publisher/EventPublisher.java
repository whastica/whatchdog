package com.devInnovators.Whatchdog.Command.infra.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.CreateReportEvent;
import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.DeleteReportEvent;
import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.UpdateReportEvent;

@Component
public class EventPublisher {
       // Inyectar el RabbitTemplate
    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCreateReportEvent(CreateReportEvent reportDTO) {
        rabbitTemplate.convertAndSend("reporte_exchange", "reporte.creado", reportDTO);
    }

    public void publishUpdateReportEvent(UpdateReportEvent reportDTO) {
        rabbitTemplate.convertAndSend("reporte_exchange", "reporte.actualizado", reportDTO);
    }

    public void publishDeleteReportEvent(DeleteReportEvent reportDTO) {
        rabbitTemplate.convertAndSend("reporte_exchange", "reporte.eliminado", reportDTO);
    }
    

    
}
