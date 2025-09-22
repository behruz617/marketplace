package az.gov.marketplace.auth.dto;

import az.gov.marketplace.auth.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    private String email;
    private String password;
    private String name;
    private String phone;
    private Role role;


}
