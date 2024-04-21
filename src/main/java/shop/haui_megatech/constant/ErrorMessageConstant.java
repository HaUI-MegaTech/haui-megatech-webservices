package shop.haui_megatech.constant;

public class ErrorMessageConstant {

    public static class User {
        public static final String NOT_FOUND           = "user.error.not-found";
        public static final String WRONG_PASSWORD      = "user.error.wrong-password";
        public static final String MISMATCHED_PASSWORD = "user.error.mismatched-password";
    }

    public static class Product {
        public static final String NOT_FOUND = "product.error.not-found";
    }

    public static class Request {
        public static final String BLANK_USERNAME      = "request.error.blank-username";
        public static final String BLANK_PASSWORD      = "request.error.blank-password";
        public static final String NEGATIVE_PAGE_INDEX = "request.error.negative-page-index";
        public static final String NULL_REQUEST        = "request.error.null-request";
        public static final String DUPLICATE_USERNAME  = "request.error.duplicate-username";
    }

}
