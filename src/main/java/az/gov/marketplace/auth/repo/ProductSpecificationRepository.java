package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.entity.ProductSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSpecificationRepository extends JpaRepository<ProductSpecification,Long> {
}
