package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.Product;
import az.gov.marketplace.auth.domain.Role;
import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.ProductResponse;
import az.gov.marketplace.auth.repo.ProductRepository;
import az.gov.marketplace.auth.repo.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Endpoints for managing products (list,add,delete)")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    @Operation(summary = "List all products",
            description = "Returns a list of all available products in the marketplace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden-missing permission")
    })
    public List<ProductResponse> listProducts() {

        return productRepo.findAll()
                .stream()
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getSeller().getEmail()
                ))
                .toList();
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    @Operation(summary = "Delete a product", description = "Admin cand delete any product ,seller can only delete their own product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden -not allowed to delete this product"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public String deleteProduct(@PathVariable Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));


        if (currentUser.getRole() == Role.ADMIN) {
            productRepo.delete(product);
            return "Admin deleted product with id: " + id;
        }

        if (currentUser.getRole() == Role.SELLER && product.getSeller().getId().equals(currentUser.getId())) {
            productRepo.delete(product);
            return "Seller deleted product with id: " + id;
        }

        throw new RuntimeException("You cannot delete this product");
    }
}