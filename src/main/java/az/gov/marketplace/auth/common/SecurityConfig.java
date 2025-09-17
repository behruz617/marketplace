package az.gov.marketplace.auth.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // hələlik sadəlik üçün
                .authorizeHttpRequests(auth -> auth
                        // Swagger-i və auth endpointlərini açırıq
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger/**",
                                "/api/auth/**"
                        ).permitAll()
                        // indi hər şeyi açıq buraxırıq (JWT gələndə dəyişəcəyik)
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults())   // default login lazım deyil, amma zərər də vermir
                .formLogin(form -> form.disable());     // login formunu söndürürük
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
