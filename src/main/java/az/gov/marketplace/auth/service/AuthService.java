package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.dto.RegisterRequest;
import az.gov.marketplace.auth.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest reg);
}
