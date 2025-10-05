package az.gov.marketplace.auth.config;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE="order.exchange";
    public static final String ROUTING_KEY="order.created";

    public static final String SELLER_QUEUE="order.queue.seller";
    public static final String ADMIN_QUEUE="order.queue.admin";

    @Bean
    public DirectExchange orderExchange(){
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue sellerQueue(){
        return new Queue(SELLER_QUEUE,true);
    }
    @Bean
    public Queue adminQueue(){
        return new Queue(ADMIN_QUEUE,true);
    }

    @Bean
    public Binding bindingSeller(Queue sellerQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(sellerQueue).to(orderExchange).with(ROUTING_KEY);
    }
    @Bean
    public Binding bindingAdmin(Queue adminQueue,DirectExchange orderExchange){
        return BindingBuilder.bind(adminQueue).to(orderExchange).with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jacksonConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf,MessageConverter converter){
        RabbitTemplate t=new RabbitTemplate(cf);
        t.setMessageConverter(converter);
        return t;
    }

}
