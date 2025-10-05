package az.gov.marketplace.auth.dto.request;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;

}
