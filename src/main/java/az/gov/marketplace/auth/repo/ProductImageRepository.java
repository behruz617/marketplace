package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
}
