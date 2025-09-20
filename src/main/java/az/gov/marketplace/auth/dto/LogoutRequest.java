package az.gov.marketplace.auth.dto;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;

}
