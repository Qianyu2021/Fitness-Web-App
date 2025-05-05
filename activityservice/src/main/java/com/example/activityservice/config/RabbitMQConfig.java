package com.example.activityservice.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Data
    public static class Exchange {
        private String name;
    }

    @Data
    public static class QueueConfig {
        private String name;
    }

    @Data
    public static class Routing {
        private String key;
    }

//Initializes it with the name specified in your YAML
    //A topic exchange routes messages to queues based on wildcard matches between the routing key
    // and the queue binding pattern
    @Bean
    public TopicExchange activityExchange() {
        return new TopicExchange(exchangeName);
    }


    // The second parameter true makes the queue durable (persisted to disk),
    // so it survives broker restarts
    @Bean
    public Queue activityQueue() {

        return new Queue(queueName, true);
    }

    //The queue name is specified in your YAML
    //Creates a Binding that connects your queue to your exchange
    //Spring automatically injects the queue and exchange beans you created above
    //The binding uses the routing key from your YAML (activity-tracking)
    //This means messages sent to activity-exchange with routing key activity-tracking will be delivered to activity-queue
    @Bean
    public Binding binding(Queue activityQueue, TopicExchange activityExchange) {
        return BindingBuilder
                .bind(activityQueue)
                .to(activityExchange)
                .with(routingKey);
    }


    //Creates a bean for converting between Java objects and message payloads
    //Uses Jackson2 library to convert objects to/from JSON
    //This allows you to send Java objects (like your ActivityMessage class) directly through RabbitMQ,
    // and they'll be automatically serialized to JSON and deserialized on the receiving end
    @Bean
    public MessageConverter messageConverter() {

        return new Jackson2JsonMessageConverter();
    }

    @PostConstruct
    public void init() {
        System.out.println("RabbitMQConfig loaded");
    }
}