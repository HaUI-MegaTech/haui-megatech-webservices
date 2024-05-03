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

    public static final class PathVariableName {
        public static final String LOGINED_USER_ID = "logined-user-id";
        public static final String CART_ITEM_ID    = "cart-item-id";
        public static final String BRAND_ID        = "brand-id";
        public static final String PRODUCT_ID      = "product-id";
        public static final String USER_ID         = "user-id";
    }


    public static final class User {
        public static final String PREFIX              = "/users";
        public static final String LOGINED_USER_ID     = "/{logined-user-id}";
        public static final String USER_ID             = "/{user-id}";
        public static final String GET_ONE             = PREFIX + "/{userId}";
        public static final String GET_ACTIVE_LIST     = PREFIX + ACTIVE;
        public static final String GET_DELETED_LIST    = PREFIX + DELETED;
        public static final String ADD_ONE             = PREFIX + LOGINED_USER_ID;
        public static final String IMPORT_EXCEL        = PREFIX + LOGINED_USER_ID + IMPORT + EXCEL;
        public static final String IMPORT_CSV          = PREFIX + LOGINED_USER_ID + IMPORT + CSV;
        public static final String UPDATE_INFO         = PREFIX + LOGINED_USER_ID + "/update-info" + USER_ID;
        public static final String UPDATE_PASSWORD     = PREFIX + LOGINED_USER_ID + "/update-password" + USER_ID;
        public static final String RESET_PASSWORD_ONE  = PREFIX + LOGINED_USER_ID + "/reset-password" + USER_ID;
        public static final String RESET_PASSWORD_LIST = PREFIX + LOGINED_USER_ID + "/reset-password";
        public static final String SOFT_DELETE_ONE     = PREFIX + LOGINED_USER_ID + SOFT_DELETE + USER_ID;
        public static final String SOFT_DELETE_LIST    = PREFIX + LOGINED_USER_ID + SOFT_DELETE;
        public static final String RESTORE_ONE         = PREFIX + LOGINED_USER_ID + RESTORE + USER_ID;
        public static final String RESTORE_LIST        = PREFIX + LOGINED_USER_ID + RESTORE;
        public static final String HARD_DELETE_ONE     = PREFIX + LOGINED_USER_ID + HARD_DELETE + USER_ID;
        public static final String HARD_DELETE_LIST    = PREFIX + LOGINED_USER_ID + HARD_DELETE;
    }

    public static final class Product {
        public static final String PREFIX                   = "/products";
        public static final String PRODUCT_ID               = "/{product-id}";
        public static final String GET_DETAIL_ONE           = PREFIX + PRODUCT_ID;
        //        public static final String GET_LIST_BY_IDS =
        public static final String GET_ACTIVE_LIST          = PREFIX + ACTIVE;
        public static final String GET_HIDDEN_LIST          = PREFIX + HIDDEN;
        public static final String GET_DELETED_LIST         = PREFIX + DELETED;
        public static final String GET_ACTIVE_LIST_BY_BRAND = Brand.PREFIX + Brand.BRAND_ID + Product.PREFIX + ACTIVE;
        public static final String ADD_ONE                  = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX;
        public static final String IMPORT_EXCEL             = User.PREFIX + User.LOGINED_USER_ID + PREFIX + IMPORT + EXCEL;
        public static final String IMPORT_CSV               = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + IMPORT + CSV;
        public static final String UPDATE_ONE               = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + UPDATE + PRODUCT_ID;
        public static final String UPDATE_LIST_FROM_EXCEL   = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + UPDATE + EXCEL;
        public static final String HIDE_ONE                 = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + HIDE + PRODUCT_ID;
        public static final String HIDE_LIST                = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + HIDE;
        public static final String EXPOSE_ONE               = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + EXPOSE + PRODUCT_ID;
        public static final String EXPOSE_LIST              = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + EXPOSE;
        public static final String SOFT_DELETE_ONE          = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + SOFT_DELETE + PRODUCT_ID;
        public static final String SOFT_DELETE_LIST         = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + SOFT_DELETE;
        public static final String RESTORE_ONE              = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + RESTORE + PRODUCT_ID;
        public static final String RESTORE_LIST             = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + RESTORE;
        public static final String HARD_DELETE_ONE          = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + HARD_DELETE + PRODUCT_ID;
        public static final String HARD_DELETE_LIST         = User.PREFIX + User.LOGINED_USER_ID + Product.PREFIX + HARD_DELETE;
    }

    public static final class CartItem {
        public static final String PREFIX           = "/cart-items";
        public static final String CART_ITEM_ID     = "/{cart-item-id}";
        public static final String GET_LIST         = PREFIX;
        public static final String ADD_ONE          = User.PREFIX + User.LOGINED_USER_ID + CartItem.PREFIX;
        public static final String UPDATE_CART_ITEM = User.PREFIX + User.LOGINED_USER_ID + CartItem.PREFIX + CART_ITEM_ID;
        public static final String DELETE_ONE       = User.PREFIX + User.LOGINED_USER_ID + CartItem.PREFIX + CART_ITEM_ID;
        public static final String DELETE_LIST      = User.PREFIX + User.LOGINED_USER_ID + CartItem.PREFIX;
    }

    public static final class Brand {
        public static final String PREFIX          = "/brands";
        public static final String BRAND_ID        = "/{brand-id}";
        public static final String GET_ONE         = PREFIX + BRAND_ID;
        public static final String GET_ACTIVE_LIST = PREFIX + ACTIVE;
    }

    public static final class Auth {
        public static final String PREFIX       = "/auth";
        public static final String REGISTER     = PREFIX + "/register";
        public static final String AUTHENTICATE = PREFIX + "/authenticate";


    }

}
