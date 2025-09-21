package az.gov.marketplace.auth.repo;

import az.gov.marketplace.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByTokenAndRevokedFalse(String token);

}
