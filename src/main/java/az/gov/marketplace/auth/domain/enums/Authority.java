package az.gov.marketplace.auth.domain.enums;

public enum Authority {

    PRODUCT_READ,
    PRODUCT_CREATE,
    PRODUCT_UPDATE,
    PRODUCT_DELETE,

    ORDER_CREATE,
    ORDER_READ,
    ORDER_DELETE,

    PROFILE_READ,
    PROFILE_UPDATE,

    USER_MANAGE, //ONLY ADMIN SEENS
    VIEW_STATS   //ONLY ADMIN SEENS

}
