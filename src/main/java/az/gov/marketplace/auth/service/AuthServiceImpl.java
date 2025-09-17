package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.RegisterRequest;
import az.gov.marketplace.auth.dto.UserResponse;
import az.gov.marketplace.auth.mapper.UserMapper;
import az.gov.marketplace.auth.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl  implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest reg) {
            if (userRepository.existsByEmail(reg.getEmail())) {
                throw new IllegalArgumentException("Email already exists");
            }
            User u=userMapper.toEntity(reg);
            u.setPassword(passwordEncoder.encode(reg.getPassword()));
            User saved=userRepository.save(u);
            return userMapper.toResponse(saved);
    }
}
