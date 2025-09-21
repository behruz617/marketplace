package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.RefreshToken;
import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.repo.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepo;

    //1. Yeni refresh token DB-yə yazmaq
    public RefreshToken createRefreshToken(User user, String token, LocalDateTime expiryDate){
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(expiryDate);
        return refreshTokenRepo.save(refreshToken);
    }

    //2.DB-de refresh token axtarmaq
    public Optional<RefreshToken>findValidToken(String token){
        return refreshTokenRepo.findByTokenAndRevokedFalse(token)
                .filter(rt->rt.getExpiryDate().isAfter(LocalDateTime.now()));
    }

    //3.refresh tokeni revoke etmek
    public void revokeToken(RefreshToken token){
        token.setRevoked(true);
        refreshTokenRepo.save(token);
    }

}
