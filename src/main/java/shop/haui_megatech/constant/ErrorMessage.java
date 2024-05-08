package shop.haui_megatech.constant;

public class ErrorMessage {

    public static class User {
        public static final String NOT_FOUND                 = "user.error.not-found";
        public static final String WRONG_PASSWORD            = "user.error.wrong-password";
        public static final String MISMATCHED_PASSWORD       = "user.error.mismatched-password";
        public static final String DEFICIENT_USERNAME_LENGTH = "user.error.deficient-username-length";
        public static final String UPDATE_INFO               = "user.error.update-info";
    }

    public static class Product {
        public static final String NOT_FOUND = "product.error.not-found";
    }

    public static class Brand {
        public static final String NOT_FOUND = "brand.error.not-found";
    }

    public static class Cart {
        public static final String NOT_FOUND = "cart.error.not-found";
    }

    public static class Order {
        public static final String NOT_FOUND = "order.error.not-found";
    }

    public static class Request {
        public static final String BLANK_USERNAME              = "request.error.blank-username";
        public static final String BLANK_PASSWORD              = "request.error.blank-password";
        public static final String NEGATIVE_PAGE_INDEX         = "request.error.negative-page-index";
        public static final String NULL_REQUEST                = "request.error.null-request";
        public static final String DUPLICATE_USERNAME          = "request.error.duplicate-username";
        public static final String MALFORMED_FILE              = "request.error.malformed-file";
        public static final String NEGATIVE_CART_ITEM_QUANTITY = "request.error.negative-cart-item-quantity";
        public static final String NEGATIVE_CART_ITEM_ID       = "request.error.negative-cart-item-id";
    }

    public static class Auth {
        public static final String AUTHENTICATE  = "auth.error.authenticate";
        public static final String EXPIRED_TOKEN = "auth.error.expired-token";
        public static final String UNAUTHORIZED  = "auth.error.unauthorized";
        public static final String MALFORMED     = "auth.error.malformed";
    }

    public static class Import {
        public static final String PROCESS_EXCEL = "import.error.process-excel";
        public static final String PROCESS_CSV   = "import.error.process-csv";

    }
}
