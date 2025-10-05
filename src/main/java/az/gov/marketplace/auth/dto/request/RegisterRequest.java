package az.gov.marketplace.auth.dto.request;

import az.gov.marketplace.auth.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private String name;
    private String phone;
    private Role role;


}
