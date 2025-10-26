package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.entity.Cart;
import az.gov.marketplace.auth.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,Long> {

    Optional<Cart>findByUser(User user);
    Optional<Cart>  findByUserEmail(String email);
}
