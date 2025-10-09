package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.entity.Product;
import az.gov.marketplace.auth.domain.entity.ProductImage;
import az.gov.marketplace.auth.domain.entity.User;
import az.gov.marketplace.auth.dto.request.ProductRequest;
import az.gov.marketplace.auth.dto.response.ProductImageResponse;
import az.gov.marketplace.auth.dto.response.ProductResponse;
import az.gov.marketplace.auth.mapper.ProductImageMapper;
import az.gov.marketplace.auth.repo.ProductImageRepository;
import az.gov.marketplace.auth.repo.ProductRepository;
import az.gov.marketplace.auth.service.ProductImageService;
import az.gov.marketplace.auth.service.ProductService;
import az.gov.marketplace.auth.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/seller")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Seller ", description = "Endpoints for seller-specific actions like adding,deleted,looking own products")
public class SellerController {

    private final ProductService productService;
    private final ProductImageRepository productImageRepo;
    private final ProductImageService productImageService;
    private final ProductImageMapper productImageMapper;
    private final ProductRepository productRepo;
    private final S3Service s3Service;

    @PostMapping(value = "/add")
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @Operation(summary = "Add new product ", description = "Allows a seller or admin to create a new product in the marketplace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "403", description = "Forbidden - missing permission")
    })

    public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest request) {
        ProductResponse productResponse = productService.addProductWithSpecs(request);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping(
            value = "/{productId}/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<List<ProductImageResponse>> uploadProductImages(
            @PathVariable Long productId,
            @RequestPart("files") List<MultipartFile> files,
            @AuthenticationPrincipal User currentUser
    ) throws IOException {
        Product product=productRepo.findById(productId)
                .orElseThrow(()->new RuntimeException("Product not found"));
        if (!product.getSeller().getId().equals(currentUser.getId())){
            throw new AccessDeniedException("you can only upload images for your own products ");
        }
        List<ProductImage>savedImages=productImageService.uploadImages(productId,files);
        List<ProductImageResponse>responses=productImageMapper.toResponse(savedImages);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/myProducts")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<List<ProductResponse>> getMyProduct(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(productService.getMyProducts(currentUser.getId()));
    }

}
