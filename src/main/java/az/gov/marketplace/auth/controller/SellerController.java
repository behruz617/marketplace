package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.entity.User;
import az.gov.marketplace.auth.dto.request.ProductRequest;
import az.gov.marketplace.auth.dto.response.ProductResponse;
import az.gov.marketplace.auth.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Seller ", description = "Endpoints for seller-specific actions like adding,deleted,looking own products")
public class SellerController {

    private final ProductService productService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @Operation(summary = "Add new product", description = "Allows a seller or admin to create a new product in the marketplace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "403", description = "Forbidden - missing permission")
    })
    public ResponseEntity<ProductResponse> add(@RequestBody ProductRequest req) {
        return ResponseEntity.ok(productService.addProductWithSpecs(req));
    }

    @GetMapping("/myProducts")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public ResponseEntity<List<ProductResponse>> getMyProduct(Authentication authentication){
        User currentUser=(User)authentication.getPrincipal();
        return ResponseEntity.ok(productService.getMyProducts(currentUser.getId()));
    }

}
