package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
