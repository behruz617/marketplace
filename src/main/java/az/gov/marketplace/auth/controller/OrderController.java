package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.entity.User;
import az.gov.marketplace.auth.dto.request.OrderRequest;
import az.gov.marketplace.auth.dto.response.OrderResponse;
import az.gov.marketplace.auth.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Endpoints for managing customer orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/createOrder")
    @Operation(summary = "Create new order", description = "User can create an order with products")
    @PreAuthorize("hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public OrderResponse createOrder(@RequestBody OrderRequest request, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return orderService.createOrder(currentUser, request);
    }

    @GetMapping("/myOrders")
    @Operation(summary = "Get my orders", description = "User can see only their own orders")
    @PreAuthorize("hasRole('USER')")
    public List<OrderResponse> getMyOrders(Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        return orderService.getMyOrders(currentUser);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(
            summary = "Delete an order",
            description = "User can only delete their own orders,while admin can delete any order"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Forbidden – you cannot delete this order"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    public ResponseEntity<Map<String, String>> deleteOrder(@PathVariable Long id, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();
        orderService.deleteOrder(id, currentUser);

        return ResponseEntity.ok(Map.of("message", "Order deleted successfully"));


    }

}
