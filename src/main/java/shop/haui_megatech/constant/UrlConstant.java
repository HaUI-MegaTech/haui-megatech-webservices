package shop.haui_megatech.constant;

public class UrlConstant {
    private static final String ACTIVE  = "/active";
    private static final String DELETED = "/deleted";

    public static class User {
        private static final String PREFIX                  = "/users";
        public static final  String GET_ACTIVE_USERS        = PREFIX + ACTIVE;
        public static final  String GET_DELETED_USERS       = PREFIX + DELETED;
        public static final  String GET_USER_BY_ID          = PREFIX + "/{userId}";
        public static final  String CREATE_USER             = PREFIX;
        public static final  String UPDATE_USER_INFO        = PREFIX + "/{userId}";
        public static final  String UPDATE_USER_PASSWORD    = PREFIX + "/change-password/{userId}";
        public static final  String TEMPORARILY_DELETE_USER = PREFIX + "/temporarily-delete/{userId}";
        public static final  String PERMANENTLY_DELETE_USER = PREFIX + "/permanently-delete/{userId}";
        public static final  String RESTORE_USER_BY_ID      = PREFIX + "/restore/{userId}";

    }

    public static class Product {
        private static final String PREFIX            = "/products";
        public static final  String GET_PRODUCTS      = PREFIX;
        public static final  String GET_PRODUCT_BY_ID = PREFIX + "/{productId}";
        public static final  String CREATE_PRODUCT    = PREFIX;
        public static final  String UPDATE_PRODUCT    = PREFIX + "/{productId}";
        public static final  String DELETE_PRODUCT    = PREFIX + "/{productId}";

    }

    public static class Auth {
        private static final String PREFIX       = "/auth";
        public static final  String REGISTER     = PREFIX + "/register";
        public static final  String AUTHENTICATE = PREFIX + "/authenticate";


    }

}
