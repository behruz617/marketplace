package az.gov.marketplace.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent implements Serializable {

    private Long productId;


    @Override
    public String toString() {
        return "OrderEvent{orderId= " + productId + '}';
    }
}
