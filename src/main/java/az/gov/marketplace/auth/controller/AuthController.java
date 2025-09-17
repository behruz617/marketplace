package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.dto.LoginRequest;
import az.gov.marketplace.auth.dto.LoginResponse;
import az.gov.marketplace.auth.dto.RegisterRequest;
import az.gov.marketplace.auth.dto.UserResponse;
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
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest req){
        UserResponse resp=authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }


    @GetMapping("/token")
    public String generateToken(@RequestParam String email){
        return jwtService.generateToken(email);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam String token,@RequestParam String email){
        boolean valid=jwtService.isTokenValid(token,email);
        return valid?"valid":"invalid";
    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
        return ResponseEntity.ok(authService.login(req));
    }
}
