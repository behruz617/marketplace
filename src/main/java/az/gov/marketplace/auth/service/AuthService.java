package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.dto.*;

public interface AuthService {

    UserResponse register(RegisterRequest reg);
    LoginResponse login(LoginRequest loginReq);
    void logout(LogoutRequest logOutReq);
    LoginResponse refresh(RefreshRequest refreshRequest);

}
