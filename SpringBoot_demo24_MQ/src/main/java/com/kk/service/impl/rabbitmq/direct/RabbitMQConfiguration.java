package com.kk.service.impl.rabbitmq.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    //交给容器管理交换机对象，交换机负责绑定管理队列，并将消息推送到队列中
    public DirectExchange getDirectExchange() {
        return new DirectExchange("directExchange");
    }

    @Bean
    //创建队列1
    public Queue getDirectQueue1() {
        //durable：是否持久化，默认为 false
        //exclusive：是否设定当前连接为专用，默认为 false ，连接关闭后队列即被删除
        //autoDelete：是否自动删除，当生产者或消费者不再使用该队列时自动删除
        return new Queue("directQueue1");
    }

    @Bean
    //创建队列2
    public Queue getDirectQueue2() {
        return new Queue("directQueue2");
    }

    @Bean
    //交换机绑定队列1 ---> 交换机+队列 ---> 绑定形成路由
    public Binding bindingDirectQueue1() {
        return BindingBuilder.bind(getDirectQueue1()).to(getDirectExchange()).with("direct1");
    }

    @Bean
    //交换机绑定队列2 ---> 交换机+队列 ---> 绑定形成路由
    public Binding bindingDirectQueue2() {
        return BindingBuilder.bind(getDirectQueue2()).to(getDirectExchange()).with("direct2");
    }
}
