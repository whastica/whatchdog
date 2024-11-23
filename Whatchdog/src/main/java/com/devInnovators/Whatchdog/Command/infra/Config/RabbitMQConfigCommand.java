package com.devInnovators.Whatchdog.Command.infra.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import org.springframework.amqp.core.Binding;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;


@Configuration
public class RabbitMQConfigCommand {
     //Canal de comunicación
    public static final String EXCHANGE_NAME = "reporte_exchange";
    //Cola de mensajes
    public static final String QUEUE_REPORTE_CREADO = "reporteCreadoQueue";
    public static final String QUEUE_REPORTE_ACTUALIZADO = "reporteActualizadoQueue";
    public static final String QUEUE_REPORTE_ELIMINADO = "reporteEliminadoQueue";
    public static final String QUEUE_REPORTE_REVISADO = "reporteRevisadoQueue";

    //Rutas de los mensajes
    public static final String ROUTING_KEY_REPORTE_CREADO = "reporte.creado";
    public static final String ROUTING_KEY_REPORTE_ACTUALIZADO = "reporte.actualizado";
    public static final String ROUTING_KEY_REPORTE_ELIMINADO = "reporte.eliminado";
    public static final String ROUTING_KEY_REPORTE_REVISADO = "reporte.revisado";

    @Bean
    public TopicExchange exchangeCommand() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverterCommand() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplateCommand(ConnectionFactory connectionFactoryCommand) {
        RabbitTemplate rabbitTemplateCommand = new RabbitTemplate(connectionFactoryCommand);
        rabbitTemplateCommand.setMessageConverter(messageConverterCommand());
        return rabbitTemplateCommand;
    }

    @Bean
    public Queue reporteCreateQueue() {
        return new Queue(QUEUE_REPORTE_CREADO);
    }

    @Bean
    public Queue reporteActualizadoQueue() {
        return new Queue(QUEUE_REPORTE_ACTUALIZADO);
    }

    @Bean
    public Queue reporteEliminadoQueue() {
        return new Queue(QUEUE_REPORTE_ELIMINADO);
    }
    
    @Bean
    public Queue reporteRevisadoQueue() {
        return new Queue(QUEUE_REPORTE_REVISADO);
    }

   

    @Bean
    public Binding bindingReporteCreado(Queue reporteCreateQueue, TopicExchange exchangeCommand) {
        return BindingBuilder.bind(reporteCreateQueue).to(exchangeCommand).with(ROUTING_KEY_REPORTE_CREADO);
    }

    @Bean
    public Binding bindingReporteActualizado(Queue reporteActualizadoQueue, TopicExchange exchangeCommand) {
        return BindingBuilder.bind(reporteActualizadoQueue).to(exchangeCommand).with(ROUTING_KEY_REPORTE_ACTUALIZADO);
    }

    @Bean
    public Binding bindingReporteEliminado(Queue reporteEliminadoQueue, TopicExchange exchangeCommand) {
        return BindingBuilder.bind(reporteEliminadoQueue).to(exchangeCommand).with(ROUTING_KEY_REPORTE_ELIMINADO);
    }

    @Bean
    public Binding bindingRevisado(Queue reporteRevisadoQueue, TopicExchange exchangeCommand) {
        return BindingBuilder.bind(reporteRevisadoQueue).to(exchangeCommand).with(ROUTING_KEY_REPORTE_REVISADO);
    }
}
