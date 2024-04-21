package shop.haui_megatech.constant;

public class UrlConstant {
    public static final  String API_V1  = "/api/v1";
    private static final String ACTIVE  = "/active";
    private static final String DELETED = "/deleted";

    public static class User {
        public static final String PREFIX               = "/users";
        public static final String GET_ACTIVE_USERS     = PREFIX + ACTIVE;
        public static final String GET_DELETED_USERS    = PREFIX + DELETED;
        public static final String GET_USER_BY_ID       = PREFIX + "/{userId}";
        public static final String CREATE_USER          = PREFIX;
        public static final String UPDATE_USER_INFO     = PREFIX + "/{userId}";
        public static final String UPDATE_USER_PASSWORD = PREFIX + "/update-password/{userId}";
        public static final String SOFT_DELETE_USER     = PREFIX + "/soft-delete/{userId}";
        public static final String HARD_DELETE_USER     = PREFIX + "/hard-delete/{userId}";
        public static final String RESTORE_USER_BY_ID   = PREFIX + "/restore/{userId}";

    }

    public static class Product {
        public static final String PREFIX            = "/products";
        public static final String GET_PRODUCTS      = PREFIX;
        public static final String GET_PRODUCT_BY_ID = PREFIX + "/{productId}";
        public static final String CREATE_PRODUCT    = PREFIX;
        public static final String UPDATE_PRODUCT    = PREFIX + "/{productId}";
        public static final String DELETE_PRODUCT    = PREFIX + "/{productId}";

    }

    public static class Auth {
        public static final String PREFIX       = "/auth";
        public static final String REGISTER     = PREFIX + "/register";
        public static final String AUTHENTICATE = PREFIX + "/authenticate";


    }

}
