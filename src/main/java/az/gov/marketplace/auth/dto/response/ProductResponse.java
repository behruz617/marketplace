package az.gov.marketplace.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private long id;
    private String name;
    private BigDecimal price;
    private String color;
    private String model;
    private Integer stockCount;
    private CategoryResponse category;
    private UserResponse seller;
    private List<SpecificationResponse> specifications;
    private List<ProductImageResponse>images;

}
