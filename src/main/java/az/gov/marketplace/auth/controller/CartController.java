package az.gov.marketplace.auth.controller;

import az.gov.marketplace.auth.domain.entity.Cart;
import az.gov.marketplace.auth.dto.response.CartResponse;
import az.gov.marketplace.auth.mapper.CartMapper;
import az.gov.marketplace.auth.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartMapper cartMapper;
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity,
            Principal principal) {
        Cart cart = cartService.addItemCart(principal.getName(), productId, quantity);
        CartResponse response = cartMapper.toCartResponse(cart);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/view")
    public ResponseEntity<CartResponse> viewCart(Principal principal) {
        Cart cart = cartService.getCartByUserName(principal.getName());
        CartResponse response = cartMapper.toCartResponse(cart);
        return ResponseEntity.ok(response);
    }
}
