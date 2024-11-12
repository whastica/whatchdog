package com.devInnovators.WatchdogRevisionPriorizacion.infra.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.CreateIssueEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.RevisedReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.application.eventDTO.UpdateReportEvent;
import com.devInnovators.WatchdogRevisionPriorizacion.infra.config.RabbitMQConfig;

public class EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // Publica el evento RevisedReportEvent en la cola correspondiente
    public void publishRevisedReportEvent(RevisedReportEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,               // Nombre del intercambio
            RabbitMQConfig.ROUTING_KEY_REPORTE_REVISADO, // Routing key para RevisedReportEvent
            event                                       // Objeto del evento a enviar
        );
        System.out.println("Publicado evento ReporteRevisado: " + event);
    }

    // Publica el evento CreateIssueEvent en la cola correspondiente
    public void publishCreateIssueEvent(CreateIssueEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,               // Nombre del intercambio
            RabbitMQConfig.ROUTING_KEY_PROBLEMA_CREADO, // Routing key para CreateIssueEvent
            event                                       // Objeto del evento a enviar
        );
        System.out.println("Publicado evento ProblemaCreado: " + event);
    }

    // Publica el evento UpdateReportEvent en la cola correspondiente
    public void publishUpdateReportEvent(UpdateReportEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,               // Nombre del intercambio
            RabbitMQConfig.ROUTING_KEY_REPORTE_ACTUALIZADO, // Routing key para UpdateReportEvent
            event                                       // Objeto del evento a enviar
        );
        System.out.println("Publicado evento ReporteActualizado: " + event);
    }
}
