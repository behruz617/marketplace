package az.gov.marketplace.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {

    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal subtotal;


}
