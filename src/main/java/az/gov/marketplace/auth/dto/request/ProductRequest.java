package az.gov.marketplace.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ProductRequest {
    private String name;
    private BigDecimal price;
    private String color;
    private String model;
    private Integer count;
    private Long sellerId;
    private Long categoryId;


    private List<SpecificationRequest> specifications;
}
