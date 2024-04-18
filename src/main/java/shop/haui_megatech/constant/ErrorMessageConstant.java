package shop.haui_megatech.constant;

public class ErrorMessageConstant {

    public static class User {
        public static final String NOT_FOUND           = "user.error.not_found";
        public static final String WRONG_PASSWORD      = "user.error.wrong_password";
        public static final String MISMATCHED_PASSWORD = "user.error.mismatched_password";
    }

    public static class Product {
        public static final String NOT_FOUND = "product.error.not_found";
    }

    public static class Request {
        public static final String BLANK_USERNAME      = "request.error.blank_username";
        public static final String BLANK_PASSWORD      = "request.error.blank_password";
        public static final String NEGATIVE_PAGE_INDEX = "request.error.negative_page_index";
        public static final String NULL_REQUEST        = "request.error.null_request";
        public static final String DUPLICATE_USERNAME  = "request.error.duplicate_username";
    }

}
