package com.devInnovators.WatchdogRevisionPriorizacion.infra.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "reporte_exchange";

    public static final String QUEUE_REPORTE_REVISADO = "reporteRevisadoQueue";
    public static final String QUEUE_REPORTE_ACTUALIZADO = "reporteActualizadoQueue";
    public static final String QUEUE_PROBLEMA_CREADO = "problemaCreadoQueue";

    public static final String ROUTING_KEY_REPORTE_REVISADO = "reporte.revisado";
    public static final String ROUTING_KEY_REPORTE_ACTUALIZADO = "reporte.actualizado";
    public static final String ROUTING_KEY_PROBLEMA_CREADO = "problema.creado";

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Declarables topicBindings() {
        TopicExchange exchange = new TopicExchange(EXCHANGE_NAME);

        Queue reporteRevisadoQueue = QueueBuilder.durable(QUEUE_REPORTE_REVISADO).build();
        Queue reporteActualizadoQueue = QueueBuilder.durable(QUEUE_REPORTE_ACTUALIZADO).build();
        Queue problemaCreadoQueue = QueueBuilder.durable(QUEUE_PROBLEMA_CREADO).build();

        return new Declarables(
            exchange,
            reporteRevisadoQueue,
            reporteActualizadoQueue,
            problemaCreadoQueue,
            BindingBuilder.bind(reporteRevisadoQueue).to(exchange).with(ROUTING_KEY_REPORTE_REVISADO),
            BindingBuilder.bind(reporteActualizadoQueue).to(exchange).with(ROUTING_KEY_REPORTE_ACTUALIZADO),
            BindingBuilder.bind(problemaCreadoQueue).to(exchange).with(ROUTING_KEY_PROBLEMA_CREADO)
        );
    }
}