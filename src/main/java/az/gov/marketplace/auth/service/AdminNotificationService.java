package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.config.RabbitConfig;
import az.gov.marketplace.auth.dto.OrderEvent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class AdminNotificationService {

    private static final Logger log = LoggerFactory.getLogger(AdminNotificationService.class);

    @RabbitListener(queues = RabbitConfig.ADMIN_QUEUE)
    public void notifyAdmin(OrderEvent event) {
        log.info("Admin notification -->New order received.OrderId = " + event.getProductId());
    }
}
