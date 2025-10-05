package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecificationRepository extends JpaRepository<Specification,Long> {
    Optional<Specification>findByName(String name);
}
