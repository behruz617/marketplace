package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    public void createOrder(Long orderId){
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                orderId
        );
        System.out.println("Order event send ->orderId  "+orderId);
    }

}
