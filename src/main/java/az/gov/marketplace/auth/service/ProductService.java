package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.entity.*;
import az.gov.marketplace.auth.dto.request.ProductRequest;
import az.gov.marketplace.auth.dto.response.ProductResponse;
import az.gov.marketplace.auth.mapper.ProductMapper;
import az.gov.marketplace.auth.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final SpecificationRepository specificationRepo;
    private final ProductSpecificationRepository productSpecificationRepo;
    private final UserRepository userRepo;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse addProductWithSpecs(ProductRequest req) {
        User seller = userRepo.findById(req.getSellerId())
                .orElseThrow(() -> new RuntimeException("Sellet not found "));
        Category category = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(req.getName())
                .price(req.getPrice())
                .color(req.getColor())
                .model(req.getModel())
                .stockCount(req.getCount())
                .seller(seller)
                .category(category)
                .isActive(true)
                .build();
        Product finalProduct = productRepo.save(product);

        List<ProductSpecification> productSpecifications = req.getSpecifications().stream()
                .map(specReq -> {

                    Specification specification = specificationRepo.findByName(specReq.getName())
                            .orElseGet(() -> specificationRepo.save(
                                    Specification.builder().name(specReq.getName()).build()
                            ));

                    return ProductSpecification.builder()
                            .product(finalProduct)
                            .specification(specification)
                            .value(specReq.getValue())
                            .build();
                })
                .toList();
        productSpecificationRepo.saveAll(productSpecifications);

        finalProduct.setSpecifications(productSpecifications);
        return productMapper.toResponse(finalProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("product not found with id " + id));
        return productMapper.toResponse(product);
    }


    public List<ProductResponse> getAllProducts() {
        return productRepo.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public List<ProductResponse> getMyProducts(Long sellerId) {
        return productRepo.findBySellerId(sellerId)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }
    @Transactional
    public void deleteProduct(Long productId){
    Product product=productRepo.findById(productId)
            .orElseThrow(()->new RuntimeException("Product not found with id "+productId));

    }

}
