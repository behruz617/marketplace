package az.gov.marketplace.auth.dto.response;

import az.gov.marketplace.auth.domain.entity.Cart;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
@Builder
public class CartResponse {
    private Long id;
    private BigDecimal totalPrice;
    private List<CartItemResponse> items;


}
