package az.gov.marketplace.auth.domain.enums;

import java.util.Set;

public enum Role {
    USER(Set.of(
            Authority.PRODUCT_READ,
            Authority.ORDER_CREATE,
            Authority.PROFILE_READ,
            Authority.PROFILE_UPDATE,
            Authority.ORDER_DELETE
    )),
    SELLER(Set.of(
            Authority.PRODUCT_READ,
            Authority.PRODUCT_CREATE,
            Authority.PRODUCT_UPDATE,
            Authority.PRODUCT_DELETE,
            Authority.ORDER_READ,
            Authority.ORDER_CREATE,
            Authority.PROFILE_READ,
            Authority.PROFILE_UPDATE
    )),

    ADMIN(Set.of(Authority.values())); //all permission

    private final Set<Authority> authorities;

    Role(Set<Authority>authorities){
        this.authorities=authorities;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }
}
