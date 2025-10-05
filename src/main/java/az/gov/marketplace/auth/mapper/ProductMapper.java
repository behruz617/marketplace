package az.gov.marketplace.auth.mapper;

import az.gov.marketplace.auth.domain.entity.Product;
import az.gov.marketplace.auth.dto.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
//@RequiredArgsConstructor
public class ProductMapper {

    private final CategoryMapper categoryMapper;
    private final UserMapper userMapper;
    private final SpecificationMapper specificationMapper;


    public ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setPrice(product.getPrice());
        response.setColor(product.getColor());
        response.setModel(product.getModel());
        response.setStockCount(product.getStockCount());
        response.setCategory(categoryMapper.toResponse(product.getCategory()));
        response.setSeller(userMapper.toResponse(product.getSeller()));
        response.setSpecifications(specificationMapper.toResponseList(product.getSpecifications()));
        return response;
    }





}
