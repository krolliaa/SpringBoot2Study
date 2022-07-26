package com.kk.service.impl.rabbitmq.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicConfiguration {
    @Bean
    public Queue getTopicQueue1() {
        return new Queue("topicQueue1");
    }

    @Bean
    public Queue getTopicQueue2() {
        return new Queue("topicQueue2");
    }

    @Bean
    public TopicExchange getTopicExchange() {
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding topicQueueBindTopicExchange1() {
        return BindingBuilder.bind(getTopicQueue1()).to(getTopicExchange()).with("topic.*.ids");
    }

    @Bean
    public Binding topicQueueBindTopicExchange2() {
        return BindingBuilder.bind(getTopicQueue2()).to(getTopicExchange()).with("topic.order.*");
    }
}
