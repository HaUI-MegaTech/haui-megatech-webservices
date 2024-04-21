package shop.haui_megatech.constant;

public class UrlConstant {
    public static final  String API_V1  = "/api/v1";
    private static final String ACTIVE  = "/active";
    private static final String DELETED = "/deleted";

    public static class User {
        public static final String PREFIX           = "/users";
        public static final String GET_ONE          = PREFIX + "/{userId}";
        public static final String GET_ACTIVE_LIST  = PREFIX + ACTIVE;
        public static final String GET_DELETED_LIST = PREFIX + DELETED;
        public static final String ADD_ONE          = PREFIX;
        public static final String ADD_LIST         = PREFIX + "/excel/upload";
        public static final String UPDATE_INFO      = PREFIX + "/{userId}";
        public static final String UPDATE_PASSWORD  = PREFIX + "/update-password/{userId}";
        public static final String SOFT_DELETE_ONE  = PREFIX + "/soft-delete/{userId}";
        public static final String SOFT_DELETE_LIST = PREFIX + "/soft-delete";
        public static final String HARD_DELETE_ONE  = PREFIX + "/hard-delete/{userId}";
        public static final String HARD_DELETE_LIST = PREFIX + "/hard-delete";
        public static final String RESTORE_ONE      = PREFIX + "/restore/{userId}";
        public static final String RESTORE_LIST     = PREFIX + "/restore";

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
