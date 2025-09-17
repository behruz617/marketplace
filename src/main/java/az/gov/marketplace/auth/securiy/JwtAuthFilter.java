package az.gov.marketplace.auth.securiy;

import az.gov.marketplace.auth.domain.User;
import az.gov.marketplace.auth.repo.UserRepository;
import az.gov.marketplace.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Security;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // Header yoxdursa və ya "Bearer " ilə başlamırsa, ötür
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            filterChain.doFilter(request, response);

            return;
        }

        String token=authHeader.substring(7); //tokeni kes
        String email=jwtService.extractEmail(token);

        if (email!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            User user=userRepository.findByEmail(email).orElse(null);

            if (user!=null&& jwtService.isTokenValid(token,email)){
                UsernamePasswordAuthenticationToken authToken=
                        new UsernamePasswordAuthenticationToken(user,null,new ArrayList<>());

                SecurityContextHolder.getContext().setAuthentication(authToken);

            }
        }
        filterChain.doFilter(request, response);

    }
}
