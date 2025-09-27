package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.dto.UserResponse;
import az.gov.marketplace.auth.repo.UserRepository;
import az.gov.marketplace.auth.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Users", description = "Endpoints for user profile and related actions")
@RequiredArgsConstructor()
public class UserController {
    private final UserRepository userRepo;
    private final JwtService jwtService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Allows only ADMIN to view all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users fetched successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden - only ADMIN can access")
    })
    public List<User> all() {
        return userRepo.findAll();
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get current user info", description = "Returns profile details of the logged-in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info fetched successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing or invalid token")
    })
    public ResponseEntity<UserResponse> me(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        String email = jwtService.extractEmail(token);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return ResponseEntity.ok(
                UserResponse.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .createdAt(user.getCreatedAt())
                        .build()
        );
    }

}
