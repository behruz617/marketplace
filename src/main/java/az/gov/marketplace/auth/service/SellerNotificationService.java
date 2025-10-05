package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.config.RabbitConfig;
import az.gov.marketplace.auth.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SellerNotificationService {
    private static final Logger log = LoggerFactory.getLogger(SellerNotificationService.class);

    @RabbitListener(queues = RabbitConfig.SELLER_QUEUE)
    public void notifySeller(OrderEvent event) {

        log.info("Seller notification -->new Order received OrderId= " + event.getProductId());
    }
}
