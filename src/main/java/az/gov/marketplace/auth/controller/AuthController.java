package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.dto.*;
import az.gov.marketplace.auth.service.AuthService;
import az.gov.marketplace.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest req) {
        UserResponse resp = authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/generateAccessToken")
    public String generateAccessToken(@RequestParam String email) {
        return jwtService.generateAccessToken(email);
    }

    @GetMapping("/generateRefreshToken")
    public String generateRefreshToken(@RequestParam String email) {
        return jwtService.generateRefreshToken(email);
    }

    @GetMapping("/validate")
    public String validate(@RequestParam String token) {
        String email=jwtService.extractEmail(token);
        boolean valid = jwtService.isTokenValid(token, email);
        return valid ? "valid" : "invalid";
    }

    @PostMapping("/logout")
    public void logout(@RequestBody LogoutRequest logOutReq) {
        authService.logout(logOutReq);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse>refresh(@RequestBody RefreshRequest refreshRequest){
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }
}
