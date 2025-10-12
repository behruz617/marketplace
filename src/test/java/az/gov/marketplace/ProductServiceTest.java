package az.gov.marketplace;

import az.gov.marketplace.auth.domain.entity.*;
import az.gov.marketplace.auth.dto.request.ProductRequest;
import az.gov.marketplace.auth.dto.request.SpecificationRequest;
import az.gov.marketplace.auth.dto.response.ProductResponse;
import az.gov.marketplace.auth.mapper.ProductMapper;
import az.gov.marketplace.auth.repo.*;
import az.gov.marketplace.auth.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepo;
    @Mock
    private CategoryRepository categoryRepo;
    @Mock
    private SpecificationRepository specificationRepo;
    @Mock
    private ProductSpecificationRepository productSpecificationRepo;
    @Mock
    private UserRepository userRepo;
    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Should add product with specifications successfully")
    public void addProductWithSpecs_success() {
        ProductRequest req=new ProductRequest();
        req.setName("Iphone 15");
        req.setPrice(BigDecimal.valueOf(2000));
        req.setColor("Black");
        req.setModel("15pro");
        req.setCount(5);
        req.setSellerId(1L);
        req.setCategoryId(2L);

        SpecificationRequest specRequest=new SpecificationRequest();
        specRequest.setName("Memory");
        specRequest.setValue("256");
        req.setSpecifications(List.of(specRequest));

        User seller=new User();
        seller.setId(1L);
        Category category=new Category();
        category.setId(2L);

        Product product= Product.builder()
                .id(9L)
                .name("iphone 15")
                .build();
        Specification spec=Specification.builder()
                .id(5L)
                .name("Memory")
                .build();
        ProductSpecification productSpec=ProductSpecification.builder()
                .product(product)
                .specification(spec)
                .value("128 gb")
                .build();

        when(userRepo.findById(1L)).thenReturn(Optional.of(seller));
        when(categoryRepo.findById(2L)).thenReturn(Optional.of(category));
        when(productRepo.save(any(Product.class))).thenReturn(product);
        when(specificationRepo.findByName("Memory")).thenReturn(Optional.of(spec));
        when(productSpecificationRepo.saveAll(any())).thenReturn(List.of(productSpec));
        when(productMapper.toResponse(any(Product.class))).thenReturn(new ProductResponse());

        ProductResponse response=productService.addProductWithSpecs(req);

        assertNotNull(response);
        verify(productRepo).save(any(Product.class));
        verify(productSpecificationRepo).saveAll(any());
        verify(productMapper).toResponse(any(Product.class));



    }
}
