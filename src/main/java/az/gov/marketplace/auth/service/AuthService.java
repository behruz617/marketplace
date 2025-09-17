package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.dto.LoginRequest;
import az.gov.marketplace.auth.dto.LoginResponse;
import az.gov.marketplace.auth.dto.RegisterRequest;
import az.gov.marketplace.auth.dto.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest reg);
    LoginResponse login(LoginRequest loginReq);

}
