package shop.haui_megatech.constant;

public class UrlConstant {
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
    private static final String ADMIN  = "/admin";

    public static final class User {
        public static final String PREFIX              = "/users";
        public static final String GET_ONE             = PREFIX + "/{userId}";
        public static final String GET_ACTIVE_LIST     = PREFIX + ACTIVE;
        public static final String GET_DELETED_LIST    = PREFIX + DELETED;
        public static final String ADD_ONE             = PREFIX;
        public static final String IMPORT_EXCEL        = PREFIX + IMPORT + EXCEL;
        public static final String IMPORT_CSV          = PREFIX + IMPORT + CSV;
        public static final String UPDATE_INFO         = PREFIX + "/update-info/{userId}";
        public static final String UPDATE_PASSWORD     = PREFIX + "/update-password/{userId}";
        public static final String RESET_PASSWORD_ONE  = PREFIX + "/reset-password/{userId}";
        public static final String RESET_PASSWORD_LIST = PREFIX + "/reset-password";
        public static final String SOFT_DELETE_ONE     = PREFIX + SOFT_DELETE + "/{userId}";
        public static final String SOFT_DELETE_LIST    = PREFIX + SOFT_DELETE;
        public static final String RESTORE_ONE         = PREFIX + RESTORE + "/{userId}";
        public static final String RESTORE_LIST        = PREFIX + RESTORE;
        public static final String HARD_DELETE_ONE     = PREFIX + HARD_DELETE + "/{userId}";
        public static final String HARD_DELETE_LIST    = PREFIX + HARD_DELETE;
    }

    public static final class Product {
        public static final String PREFIX                   = "/products";
        public static final String GET_DETAIL_ONE           = PREFIX + "/{productId}";
        //        public static final String GET_LIST_BY_IDS =
        public static final String GET_ACTIVE_LIST          = PREFIX + ACTIVE;
        public static final String GET_HIDDEN_LIST          = PREFIX + HIDDEN;
        public static final String GET_DELETED_LIST         = PREFIX + DELETED;
        public static final String GET_ACTIVE_LIST_BY_BRAND = PREFIX + ACTIVE + "/{brandId}";
        public static final String ADD_ONE                  = PREFIX;
        public static final String IMPORT_EXCEL             = PREFIX + IMPORT + EXCEL;
        public static final String IMPORT_CSV               = PREFIX + IMPORT + CSV;
        public static final String UPDATE_ONE               = PREFIX + UPDATE + "/{productId}";
        public static final String UPDATE_LIST_FROM_EXCEL   = PREFIX + UPDATE + EXCEL;
        public static final String HIDE_ONE                 = PREFIX + HIDE + "/{productId}";
        public static final String HIDE_LIST                = PREFIX + HIDE;
        public static final String EXPOSE_ONE               = PREFIX + EXPOSE + "/{productId}";
        public static final String EXPOSE_LIST              = PREFIX + EXPOSE;
        public static final String SOFT_DELETE_ONE          = PREFIX + SOFT_DELETE + "/{productId}";
        public static final String SOFT_DELETE_LIST         = PREFIX + SOFT_DELETE;
        public static final String RESTORE_ONE              = PREFIX + RESTORE + "/{productId}";
        public static final String RESTORE_LIST             = PREFIX + RESTORE;
        public static final String HARD_DELETE_ONE          = PREFIX + HARD_DELETE + "/{productId}";
        public static final String HARD_DELETE_LIST         = PREFIX + HARD_DELETE;
    }

    public static final class Cart {
        public static final String PREFIX           = "/cart";
        public static final String GET_CART_ITEMS   = PREFIX;
        public static final String ADD_CART_ITEM    = PREFIX;
        public static final String UPDATE_CART_ITEM = PREFIX;
        public static final String DELETE_CART_ITEM = PREFIX;
    }

    public static final class Brand {
        public static final String PREFIX          = "/brands";
        public static final String GET_ONE         = PREFIX + "/{brandId}";
        public static final String GET_ACTIVE_LIST = PREFIX + ACTIVE;
    }
    public static final class Order {
        public static final String PREFIX           = "/orders";
        public static final String GET_LIST_BY_USER_ID    = PREFIX;
        public static final String GET_LIST_FOR_ADMIN     = PREFIX + ADMIN;
        public static final String GET_DETAIL_FOR_USER = PREFIX + "/detail";
        public static final String GET_DETAIL_FOR_ADMIN = PREFIX + ADMIN + "/detail" +"/{orderId}";
        public static final String ADD_ONE_FOR_USER          = PREFIX + "/addOne";
        public static final String ADD_ONE_FOR_ADMIN         = PREFIX + ADMIN + "/addOne";
        public static final String UPDATED_ONE_FOR_UER       = PREFIX + "/updatedOne";
        public static final String UPDATED_ONE_FOR_ADMIN     = PREFIX + ADMIN + "/updatedOne";
        public static final String DELETE_ONE_ORDER    = PREFIX + DELETED + "/{orderId}";
    }
    public static final class Auth {
        public static final String PREFIX       = "/auth";
        public static final String REGISTER     = PREFIX + "/register";
        public static final String AUTHENTICATE = PREFIX + "/authenticate";


    }

}
