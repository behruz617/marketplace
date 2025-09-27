package az.gov.marketplace.auth.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE="order.exchange";
    public static final String ROUTING_KEY="order.created";
    public static final String QUEUE="order.queue";

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(EXCHANGE);
    }
    @Bean
    public Queue orderQueue(){
        return new Queue(QUEUE,true);
    }
    @Bean
    public Binding binding(Queue orderQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(ROUTING_KEY);
    }

}
