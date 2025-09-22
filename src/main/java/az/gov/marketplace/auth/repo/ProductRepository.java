package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.Product;
import az.gov.marketplace.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllBySeller(User seller);
}
