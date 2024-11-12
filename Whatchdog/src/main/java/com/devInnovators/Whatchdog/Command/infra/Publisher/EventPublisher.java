package com.devInnovators.Whatchdog.Command.infra.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {
       // Inyectar el RabbitTemplate
    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishCreateReportEvent(String id) {
        rabbitTemplate.convertAndSend("reporte_exchange", "reporte.creado", id);
    }

    public void publishUpdateReportEvent(String id) {
        rabbitTemplate.convertAndSend("reporte_exchange", "reporte.actualizado", id);
    }

    public void publishDeleteReportEvent(String id) {
        rabbitTemplate.convertAndSend("reporte_exchange", "reporte.eliminado", id);
    }
    

    
}
