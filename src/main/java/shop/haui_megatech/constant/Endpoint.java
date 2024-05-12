package shop.haui_megatech.constant;

public class Endpoint {
    public static final  String API_V1      = "/api/v1";
    private static final String ACTIVE      = "/active";
    private static final String DELETED     = "/deleted";
    private static final String HIDDEN      = "/hidden";
    private static final String SOFT_DELETE = "/soft-delete";
    private static final String HARD_DELETE = "/hard-delete";
    private static final String RESTORE     = "/restore";
    private static final String IMPORT      = "/import";
    private static final String EXCEL       = "/excel";
    private static final String CSV         = "/csv";
    private static final String HIDE        = "/hide";
    private static final String EXPOSE      = "/expose";
    private static final String UPDATE      = "/update";
    private static final String ADMIN       = "/admin";


    public static final class User {
        public static final String PREFIX              = "/users";
        public static final String USER_ID             = "/{userId}";
        public static final String GET_ONE             = PREFIX + USER_ID;
        public static final String GET_ACTIVE_LIST     = PREFIX + ACTIVE;
        public static final String GET_DELETED_LIST    = PREFIX + DELETED;
        public static final String ADD_ONE             = PREFIX;
        public static final String IMPORT_EXCEL        = PREFIX + IMPORT + EXCEL;
        public static final String IMPORT_CSV          = PREFIX + IMPORT + CSV;
        public static final String UPDATE_INFO         = PREFIX + "/update-info" + USER_ID;
        public static final String UPDATE_PASSWORD     = PREFIX + "/update-password" + USER_ID;
        public static final String RESET_PASSWORD_ONE  = PREFIX + "/reset-password" + USER_ID;
        public static final String RESET_PASSWORD_LIST = PREFIX + "/reset-password";
        public static final String SOFT_DELETE_ONE     = PREFIX + SOFT_DELETE + USER_ID;
        public static final String SOFT_DELETE_LIST    = PREFIX + SOFT_DELETE;
        public static final String RESTORE_ONE         = PREFIX + RESTORE + USER_ID;
        public static final String RESTORE_LIST        = PREFIX + RESTORE;
        public static final String HARD_DELETE_ONE     = PREFIX + HARD_DELETE + USER_ID;
        public static final String HARD_DELETE_LIST    = PREFIX + HARD_DELETE;
    }

    public static final class Product {
        public static final String PREFIX                 = "/products";
        public static final String PRODUCT_ID             = "/{productId}";
        public static final String GET_DETAIL_ONE         = PREFIX + PRODUCT_ID;
        public static final String GET_ACTIVE_LIST        = PREFIX + ACTIVE;
        public static final String GET_HIDDEN_LIST        = PREFIX + HIDDEN;
        public static final String GET_DELETED_LIST       = PREFIX + DELETED;
        public static final String ADD_ONE                = PREFIX;
        public static final String IMPORT_EXCEL           = PREFIX + IMPORT + EXCEL;
        public static final String IMPORT_CSV             = PREFIX + IMPORT + CSV;
        public static final String UPDATE_ONE             = PREFIX + UPDATE + PRODUCT_ID;
        public static final String UPDATE_LIST_FROM_EXCEL = PREFIX + UPDATE + EXCEL;
        public static final String HIDE_ONE               = PREFIX + HIDE + PRODUCT_ID;
        public static final String HIDE_LIST              = PREFIX + HIDE;
        public static final String EXPOSE_ONE             = PREFIX + EXPOSE + PRODUCT_ID;
        public static final String EXPOSE_LIST            = PREFIX + EXPOSE;
        public static final String SOFT_DELETE_ONE        = PREFIX + SOFT_DELETE + PRODUCT_ID;
        public static final String SOFT_DELETE_LIST       = PREFIX + SOFT_DELETE;
        public static final String RESTORE_ONE            = PREFIX + RESTORE + PRODUCT_ID;
        public static final String RESTORE_LIST           = PREFIX + RESTORE;
        public static final String HARD_DELETE_ONE        = PREFIX + HARD_DELETE + PRODUCT_ID;
        public static final String HARD_DELETE_LIST       = PREFIX + HARD_DELETE;
    }

    public static final class CartItem {
        public static final String PREFIX           = "/cart-items";
        public static final String ID               = "/{cartItemId}";
        public static final String GET_LIST_BY_USER = User.PREFIX + User.USER_ID + PREFIX;
        public static final String ADD_ONE          = Product.PREFIX + Product.PRODUCT_ID + PREFIX;
        public static final String UPDATE_ONE       = Product.PREFIX + Product.PRODUCT_ID + PREFIX + ID;
        public static final String DELETE           = PREFIX;
    }

    public static final class Brand {
        public static final String PREFIX          = "/brands";
        public static final String BRAND_ID        = "/{brandId}";
        public static final String GET_ONE         = PREFIX + BRAND_ID;
        public static final String GET_ACTIVE_LIST = PREFIX + ACTIVE;
    }

    public static final class Invoice {
        public static final String PREFIX                = "/orders";
        public static final String GET_LIST_BY_USER_ID   = PREFIX;
        public static final String GET_LIST_FOR_ADMIN    = PREFIX + ADMIN;
        public static final String GET_DETAIL_FOR_USER   = PREFIX + "/detail";
        public static final String GET_DETAIL_FOR_ADMIN  = PREFIX + ADMIN + "/detail" + "/{orderId}";
        public static final String ADD_ONE_FOR_USER      = PREFIX + "/addOne";
        public static final String ADD_ONE_FOR_ADMIN     = PREFIX + ADMIN + "/addOne";
        public static final String UPDATED_ONE_FOR_UER   = PREFIX + "/updatedOne";
        public static final String UPDATED_ONE_FOR_ADMIN = PREFIX + ADMIN + "/updatedOne";
        public static final String DELETE_ONE_ORDER      = PREFIX + DELETED + "/{orderId}";
    }

    public static final class Auth {
        public static final String PREFIX       = "/auth";
        public static final String REGISTER     = PREFIX + "/register";
        public static final String AUTHENTICATE = PREFIX + "/authenticate";
        public static final String REFRESH      = PREFIX + "/refresh";

    }

    public static final class Location {
        public static final String PROVINCES         = "/provinces";
        public static final String PROVINCE_CODE     = "/{provinceCode}";
        public static final String DISTRICTS         = "/districts";
        public static final String DISTRICT_CODE     = "/{districtCode}";
        public static final String WARDS             = "/wards";
        public static final String GET_ALL_PROVINCES = PROVINCES;
        public static final String GET_ALL_DISTRICTS = GET_ALL_PROVINCES + PROVINCE_CODE + DISTRICTS;
        public static final String GET_ALL_WARDS     = GET_ALL_DISTRICTS + DISTRICT_CODE + WARDS;
    }

    public static final class Address {
        public static final String PREFIX      = "/addresses";
        public static final String ADDRESS_ID  = "/{addressId}";
        public static final String ADDRESS_IDS = "/{addressIds}";
        public static final String GET_LIST    = User.PREFIX + User.USER_ID + PREFIX;
        public static final String ADD_ONE     = User.PREFIX + User.USER_ID + PREFIX;
        public static final String UPDATE_ONE  = User.PREFIX + User.USER_ID + PREFIX + ADDRESS_ID;
        public static final String DELETE      = User.PREFIX + User.USER_ID + PREFIX + ADDRESS_IDS;
    }

    public static final class Feedback {
        public static final String PREFIX              = "/feedbacks";
        public static final String FEEDBACK_ID         = "/{feedbackId}";
        public static final String GET_LIST_BY_USER    = User.PREFIX + User.USER_ID + PREFIX;
        public static final String GET_LIST_BY_PRODUCT = Product.PREFIX + Product.PRODUCT_ID + PREFIX;
        public static final String ADD_ONE             = Product.PREFIX + Product.PRODUCT_ID + PREFIX;
        public static final String UPDATE_ONE          = Product.PREFIX + Product.PRODUCT_ID + PREFIX + FEEDBACK_ID;
        public static final String DELETE_ONE          = User.PREFIX + User.USER_ID + PREFIX + FEEDBACK_ID;
    }

    public static final class Payment {
        public static final String CREATE   = "/create-payment";
        public static final String CALLBACK = "/callback-payment";
    }

}
