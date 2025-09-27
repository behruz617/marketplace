package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.dto.*;
import az.gov.marketplace.auth.service.AuthService;
import az.gov.marketplace.auth.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication and token management")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user ", description = "Creates a new user with default User role ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid registration request")
    })
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest req) {
        UserResponse resp = authService.register(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user and returns access and refresh tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid email or password")
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/generateAccessToken")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Generate access token (ADMIN only)", description = "Generates a new access token for testing purpose")
    public String generateAccessToken(@RequestParam String email) {
        return jwtService.generateAccessToken(email);
    }

    @GetMapping("/generateRefreshToken")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Generate refresh token (ADMIN only)", description = "Generates a new refresh token for testing purpose")
    public String generateRefreshToken(@RequestParam String email) {
        return jwtService.generateRefreshToken(email);
    }

    @GetMapping("/validate")
    @Operation(summary = "Validate access token", description = "Checks if an access token is valid or expired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "403", description = "Token is invalid or expired")
    })
    public String validate(@RequestParam String token) {
        String email = jwtService.extractEmail(token);
        boolean valid = jwtService.isTokenValid(token, email);
        return valid ? "valid" : "invalid";
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Revokes the refresh token so it cannot be used again")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged out successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid token or user not logged in")
    })
    public void logout(@RequestBody LogoutRequest logOutReq) {
        authService.logout(logOutReq);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh token", description = "Generates a new access token using a valid refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token refreshed successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or expired refresh token")
    })
    public ResponseEntity<LoginResponse> refresh(@RequestBody RefreshRequest refreshRequest) {
        return ResponseEntity.ok(authService.refresh(refreshRequest));
    }
}
