package shop.haui_megatech.constant;

public class UrlConstant {
    public static class Product {
        private static final String PREFIX         = "/products";
        public static final  String GET_PRODUCTS   = PREFIX;
        public static final  String GET_PRODUCT    = PREFIX + "/{productId}";
        public static final  String CREATE_PRODUCT = PREFIX;
        public static final  String UPDATE_PRODUCT = PREFIX + "/{productId}";
        public static final  String DELETE_PRODUCT = PREFIX + "/{productId}";

    }

    public static class Auth {
        private static final String PREFIX       = "/auth";
        public static final  String REGISTER     = PREFIX + "/register";
        public static final  String AUTHENTICATE = PREFIX + "/authenticate";


    }

}
