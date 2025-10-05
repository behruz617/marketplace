package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.config.RabbitConfig;
import az.gov.marketplace.auth.domain.entity.Order;
import az.gov.marketplace.auth.domain.entity.OrderItem;
import az.gov.marketplace.auth.domain.entity.Product;
import az.gov.marketplace.auth.domain.entity.User;
import az.gov.marketplace.auth.domain.enums.Role;
import az.gov.marketplace.auth.dto.OrderEvent;
import az.gov.marketplace.auth.dto.request.OrderItemRequest;
import az.gov.marketplace.auth.dto.request.OrderRequest;
import az.gov.marketplace.auth.dto.response.OrderResponse;
import az.gov.marketplace.auth.mapper.OrderMapper;
import az.gov.marketplace.auth.repo.OrderRepository;
import az.gov.marketplace.auth.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepo;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepo;

    public OrderResponse createOrder(User currentUser, OrderRequest req) {
        Order order = Order.builder()
                .user(currentUser)
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<OrderItem> items = new ArrayList<>();

        for (OrderItemRequest itemRequest : req.getItems()) {
            Product product = productRepo.getReferenceById(itemRequest.getProductId());

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .price(product.getPrice())
                    .build();

            items.add(item);
        }

        order.setItems(items);
        Order saveOrder = orderRepo.save(order);
        Order fullOrder = orderRepo.findById(saveOrder.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        OrderEvent event = new OrderEvent(saveOrder.getId());
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,
                RabbitConfig.ROUTING_KEY,
                event
        );
        log.info("Order event send -> orderId {}", saveOrder.getId());
        return orderMapper.toResponse(fullOrder);
    }


    public List<OrderResponse> getMyOrders(User currentUser) {
        return orderRepo.findByUserId(currentUser.getId())
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional
    public void deleteOrder(Long orderId, User currentUser) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (currentUser.getRole() == Role.ADMIN) {
            orderRepo.delete(order);
            return;
        }
        if (currentUser.getRole() == Role.USER && order.getUser().getId().equals(currentUser.getId())) {
            orderRepo.delete(order);
            return;
        }
        throw new RuntimeException("you cannot delete permission this order");
    }
}
