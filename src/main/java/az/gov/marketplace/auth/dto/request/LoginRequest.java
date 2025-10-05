package az.gov.marketplace.auth.dto.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;


}
