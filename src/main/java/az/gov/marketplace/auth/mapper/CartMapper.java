package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.Cart;
import az.gov.marketplace.auth.domain.entity.CartItem;
import az.gov.marketplace.auth.dto.response.CartItemResponse;
import az.gov.marketplace.auth.dto.response.CartResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartMapper {

    public CartItemResponse toCartItemResponse(CartItem item) {
        return CartItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .subtotal(item.getSubTotal())
                .build();
    }

    public CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(this::toCartItemResponse)
                .toList();
        return CartResponse.builder()
                .id(cart.getId())
                .totalPrice(cart.getTotalPrice())
                .items(itemResponses)
                .build();
    }

}
