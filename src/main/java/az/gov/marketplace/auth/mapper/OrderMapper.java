package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.Order;
import az.gov.marketplace.auth.domain.entity.OrderItem;
import az.gov.marketplace.auth.dto.response.OrderItemResponse;
import az.gov.marketplace.auth.dto.response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) return null;

        List<OrderItemResponse> items = new ArrayList<>();

        for (OrderItem item : order.getItems()) {
            OrderItemResponse itemResponse = toItemResponse(item);
            items.add(itemResponse);
        }

        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .deliveredAt(order.getDeliveredAt())
                .items(items)
                .build();
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setProductId(item.getProduct().getId());
        response.setProductName(item.getProduct().getName());
        response.setPrice(item.getPrice());
        response.setQuantity(item.getQuantity());

        return response;

    }

}
