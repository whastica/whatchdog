package com.devInnovators.Whatchdog.Command.infra.Publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.CreateReportEvent;
import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.DeleteReportEvent;
import com.devInnovators.Whatchdog.Command.aplicattion.EventsDTO.UpdateReportEvent;
import com.devInnovators.Whatchdog.Command.infra.Config.RabbitMQConfigCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class EventPublisher {
       // Inyectar el RabbitTemplate
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public EventPublisher(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publishCreateReportEvent(CreateReportEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfigCommand.EXCHANGE_NAME, 
        RabbitMQConfigCommand.ROUTING_KEY_REPORTE_CREADO, event);
        System.out.println("Publicado evento ReporteCreado: " + event);
    }

    public void publishUpdateReportEvent(UpdateReportEvent event) {
        try {
            // Serializar manualmente a JSON para verificar
           // ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(event);
            System.out.println("Evento serializado a JSON: " + json);
            
            // Enviar el evento por RabbitMQ
            rabbitTemplate.convertAndSend(RabbitMQConfigCommand.EXCHANGE_NAME,
                RabbitMQConfigCommand.ROUTING_KEY_REPORTE_ACTUALIZADO, event);
            System.out.println("Publicado evento ReporteActualizado: " + event);
        } catch (JsonProcessingException e) {
            System.err.println("Error al serializar el evento: " + e.getMessage());
        }
    }

    public void publishDeleteReportEvent(DeleteReportEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfigCommand.EXCHANGE_NAME,
        RabbitMQConfigCommand.ROUTING_KEY_REPORTE_ELIMINADO, event);
        System.out.println("Publicado evento ReporteEliminado: " + event);
    }
    

    
}
