package az.gov.marketplace.auth.service;

import az.gov.marketplace.auth.dto.response.CartResponse;
import az.gov.marketplace.auth.mapper.CartMapper;
import az.gov.marketplace.auth.repo.CartRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartCacheService {

    private final CartRepository cartRepo;
    private final CartMapper cartMapper;

    @Cacheable(cacheNames = "cart", key = "'user:'+#email")
    public CartResponse getCartByUserEmail(String email) {
        System.out.println("DB-ye getdi (Redis de tapilmadi): " + email);
        return cartRepo.findByUserEmail(email)
                .map(cartMapper::toCartResponse)
                .orElseThrow(() -> new RuntimeException("Cart not found for user: " + email));
    }

    @CacheEvict(cacheNames = "cart", key = "'user:'+#email")
    public void evictCartCache(String email) {
        System.out.println("Redis cache silindi user ucun :" + email);
    }

    @CachePut(cacheNames = "cart", key = "'user:'+#email")
    public CartResponse saveCartToCache(String email, CartResponse response) {
        System.out.println("Cart redise yazildi:" + email);
        return response;
    }
}
