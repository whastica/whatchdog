package com.devInnovators.Whatchdog.Query.infra.Config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;

import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {
    
    //Canal de comunicación
    public static final String EXCHANGE_NAME = "reporte_exchange";

    //Cola de mensajes
    public static final String QUEUE_REPORTE_REVISADO = "reporteRevisadoQueue";

    //Rutas de los mensajes
    public static final String ROUTING_KEY_REPORTE_REVISADO = "reporte.revisado";

    
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Queue reporteRevisadoQueue() {
        return new Queue(QUEUE_REPORTE_REVISADO);
    }

    @Bean
    public Binding bindingRevisado(Queue reporteRevisadoQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reporteRevisadoQueue).to(exchange).with(ROUTING_KEY_REPORTE_REVISADO);
    }
}
