package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.Product;
import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.repo.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@Tag(name = "Seller ",description = "Endpoints for seller-specific actions like adding products")
public class SellerController {

    private final ProductRepository productRepo;
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    @Operation(summary = "Add new product", description = "Allows a seller or admin to create a new product in the marketplace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product request"),
            @ApiResponse(responseCode = "403", description = "Forbidden - missing permission")
    })
    public Product add(@RequestBody Product product, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        product.setSeller(currentUser);
        return productRepo.save(product);
    }


}
