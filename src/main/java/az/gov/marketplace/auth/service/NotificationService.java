package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.config.RabbitConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void notifySeller(Long orderId){

        System.out.println("Seller notification ->new Order received OrderId= "+orderId);
    }
}
