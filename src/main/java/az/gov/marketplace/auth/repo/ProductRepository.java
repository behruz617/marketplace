package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.entity.Product;
import az.gov.marketplace.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllBySeller(User seller);
    List<Product>findBySellerId(Long sellerId);

    Long seller(User seller);
}
