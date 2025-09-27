package az.gov.marketplace.auth.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@OpenAPIDefinition(
        info = @Info(
                title = "MarketPlace API",
                version = "1.0",
                description = "Swagger documentation for MarketPlace App"
        )
//        ),
//        tags = {
//                @Tag(name = "1.Authentication", description = "Endpoints for user authentication and token management"),
//                @Tag(name = "2.Users", description = "Endpoints for user profile and related actions"),
//                @Tag(name = "3.Products", description = "Endpoints for managing products (list,add,delete)"),
//                @Tag(name = "4.Seller", description = "Endpoints for seller-specific actions like adding products"),
//                @Tag(name = "5.Orders", description = "Endpoints for managing customer orders"),
//                @Tag(name = "6.Admin", description = "Endpoints for admin-specify actions and statistics")
//        }
)
public class SwaggerConfig {
}
