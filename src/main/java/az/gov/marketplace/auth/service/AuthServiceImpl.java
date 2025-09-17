package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.LoginRequest;
import az.gov.marketplace.auth.dto.LoginResponse;
import az.gov.marketplace.auth.dto.RegisterRequest;
import az.gov.marketplace.auth.dto.UserResponse;
import az.gov.marketplace.auth.mapper.UserMapper;
import az.gov.marketplace.auth.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest reg) {
            if (userRepository.existsByEmail(reg.getEmail())) {
                throw new IllegalArgumentException("Email already exist");
            }
            User u=userMapper.toEntity(reg);
            u.setPassword(passwordEncoder.encode(reg.getPassword()));
            User saved=userRepository.save(u);
            return userMapper.toResponse(saved);
    }

    @Override
    public LoginResponse login(LoginRequest req) {
            User user=userRepository.findByEmail(req.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
            if (!passwordEncoder.matches(req.getPassword(),user.getPassword())) {
                throw new IllegalArgumentException("Invalid email or password");
            }

            String token=jwtService.generateToken(user.getEmail());
            user.setLastLogin(Instant.now());
            userRepository.save(user);
            return new LoginResponse(token);


    }
}
