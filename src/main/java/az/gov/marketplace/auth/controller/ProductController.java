package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.Product;
import az.gov.marketplace.auth.domain.Role;
import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.ProductResponse;
import az.gov.marketplace.auth.repo.ProductRepository;
import az.gov.marketplace.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('PRODUCT_READ')")
    public List<ProductResponse> listProducts() {

        return productRepo.findAll()
                .stream()
                .map(p->new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getSeller().getEmail()
                ))
                .toList();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('PRODUCT_CREATE')")
    public Product add(@RequestBody Product product, Authentication authentication) {
//            String email = authentication.getName();
//            User currentUser = userRepo.findByEmail(email)
//                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
        User currentUser = (User) authentication.getPrincipal();

        product.setSeller(currentUser);
        return productRepo.save(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRODUCT_DELETE')")
    public String deleteProduct(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        User currentUser = (User)authentication.getPrincipal();
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        //admins  can delete anything
        if (currentUser.getRole() == Role.ADMIN) {
            productRepo.delete(product);
            return "Admin deleted product with id: " + id;
        }
        //seller can delet only own products
        if (currentUser.getRole() == Role.SELLER && product.getSeller().getId().equals(currentUser.getId())) {
            productRepo.delete(product);
            return "Seller deleted product with id: " + id;
        }

        throw new RuntimeException("You cannot delete this product");
    }
}