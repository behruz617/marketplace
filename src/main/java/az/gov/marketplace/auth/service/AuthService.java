package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.dto.request.LoginRequest;
import az.gov.marketplace.auth.dto.request.LogoutRequest;
import az.gov.marketplace.auth.dto.request.RefreshRequest;
import az.gov.marketplace.auth.dto.request.RegisterRequest;
import az.gov.marketplace.auth.dto.response.LoginResponse;
import az.gov.marketplace.auth.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest reg);
    LoginResponse login(LoginRequest loginReq);
    void logout(LogoutRequest logOutReq);
    LoginResponse refresh(RefreshRequest refreshRequest);

}
