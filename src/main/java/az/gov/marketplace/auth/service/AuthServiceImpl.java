package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.RefreshToken;
import az.gov.marketplace.auth.domain.Role;
import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.*;
import az.gov.marketplace.auth.mapper.UserMapper;
import az.gov.marketplace.auth.repo.RefreshTokenRepository;
import az.gov.marketplace.auth.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final JwtProperties jwtProperties;



    @Override
    @Transactional
    public UserResponse register(RegisterRequest reg) {
        if (userRepository.existsByEmail(reg.getEmail())) {
            throw new IllegalArgumentException("Email already exist");
        }
        User u = userMapper.toEntity(reg);
        u.setPassword(passwordEncoder.encode(reg.getPassword()));
        u.setRole(Role.USER);
        User saved = userRepository.save(u);
        return userMapper.toResponse(saved);
    }

    @Override
    public LoginResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        //acces token
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        //refresh token
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        refreshTokenService.createRefreshToken(
                user,
                refreshToken,
                LocalDateTime.now().plus(Duration.ofMillis(jwtProperties.getRefreshExpiration()))
        );
        user.setLastLogin(Instant.now());
        userRepository.save(user);

        return new LoginResponse(accessToken,refreshToken);

    }

    @Override
    public void logout(LogoutRequest logOutReq) {
        String refReshTokenStr=logOutReq.getRefreshToken();

        refreshTokenService.findValidToken(refReshTokenStr)
                .ifPresent(refreshTokenService::revokeToken);
    }

    @Override
    public LoginResponse refresh(RefreshRequest refreshRequest) {

       String refreshTokenStr=refreshRequest.getRefreshToken();

        RefreshToken refreshToken=refreshTokenService.findValidToken(refreshTokenStr)
                .orElseThrow(()->new IllegalArgumentException("Invalid or expired refresh token"));

        User user=refreshToken.getUser();

        String newAccessToken=jwtService.generateAccessToken(user.getEmail());
        return new LoginResponse(newAccessToken,refreshTokenStr);
    }
}
