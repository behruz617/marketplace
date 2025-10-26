package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.domain.entity.Cart;
import az.gov.marketplace.auth.domain.entity.CartItem;
import az.gov.marketplace.auth.domain.entity.Product;
import az.gov.marketplace.auth.domain.entity.User;
import az.gov.marketplace.auth.dto.response.CartResponse;
import az.gov.marketplace.auth.mapper.CartMapper;
import az.gov.marketplace.auth.repo.CartRepository;
import az.gov.marketplace.auth.repo.ProductRepository;
import az.gov.marketplace.auth.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepo;
    private final UserRepository userRepo;
    private final ProductRepository productRepo;
    private final CartCacheService cartCacheService;
    private final CartMapper cartMapper;

    public Cart addItemCart(String username, Long productId, int quantity) {
        User user = userRepo.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findById(productId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return newCart;
                });
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Map<Long, CartItem> itemMap = new HashMap<>();

        for (CartItem item : cart.getItems()) {
            itemMap.put(item.getProduct().getId(), item);
        }

        if (itemMap.containsKey(productId)) {
            CartItem existingItem = itemMap.get(productId);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setSubTotal(product.getPrice()
                    .multiply(BigDecimal.valueOf(existingItem.getQuantity())));
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setSubTotal(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
            cart.getItems().add(newItem);
        }

        BigDecimal total = cart.getItems().stream()
                .map(CartItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
        return cartRepo.save(cart);
    }

    public CartResponse getCartByUserName(String username){

        var cached=cartCacheService.getCartByUserEmail(username);
        if (cached!=null){
            System.out.println("Melumat redisden oxundu: "+username);

            return cached;
        }
        User user=userRepo.findByEmail(username)
                .orElseThrow(()->new RuntimeException("User not found with email: "+username));

        Cart cart=cartRepo.findByUser(user)
                .orElseThrow(()->new RuntimeException("Cart not found for user :" +user.getEmail()));

        CartResponse response=cartMapper.toCartResponse(cart);
        cartCacheService.saveCartToCache(username,response);
        return response;
    }
}
