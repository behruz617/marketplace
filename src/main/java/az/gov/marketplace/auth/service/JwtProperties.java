package az.gov.marketplace.auth.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.jwt")
@Data
public class JwtProperties {

    private String secret;
    private long accessExpirations;
    private long refreshExpirations;
}
