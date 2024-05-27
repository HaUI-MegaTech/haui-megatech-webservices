package shop.haui_megatech.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public enum Role {

    CUSTOMER(Set.of(
            Authority.UPDATE_INFO,

            Authority.READ_BRAND,

            Authority.READ_PRODUCT,
            Authority.READ_ACTIVE_PRODUCTS,

            Authority.READ_FEEDBACK,
            Authority.CREATE_FEEDBACK,
            Authority.UPDATE_FEEDBACK,

            Authority.CREATE_PAYMENT,

            Authority.READ_ORDER,

            Authority.CREATE_ADDRESS,
            Authority.UPDATE_ADDRESS,
            Authority.DELETE_ADDRESS,

            Authority.READ_CART_ITEM,
            Authority.CREATE_CART_ITEM,
            Authority.UPDATE_CART_ITEM,
            Authority.DELETE_CART_ITEM
    )),

    STAFF(Set.of(
            Authority.READ_PRODUCT,
            Authority.READ_ACTIVE_PRODUCTS,
            Authority.CREATE_PRODUCT,
            Authority.UPDATE_PRODUCT,

            Authority.UPDATE_INFO,
            Authority.CREATE_PAYMENT
    )),

    MANAGER(Set.of(
            Authority.READ_PRODUCT,
            Authority.READ_ACTIVE_PRODUCTS,
            Authority.READ_DELETED_PRODUCTS,
            Authority.READ_HIDDEN_PRODUCTS,
            Authority.CREATE_PRODUCT,
            Authority.UPDATE_PRODUCT,
            Authority.SOFT_DELETE_PRODUCT,
            Authority.RESTORE_PRODUCT,
            Authority.HARD_DELETE_PRODUCT,
            Authority.HIDE_PRODUCT,
            Authority.EXPOSE_PRODUCT,
            Authority.IMPORT_EXCEL_PRODUCT,
            Authority.IMPORT_CSV_PRODUCT,

            Authority.READ_USER,
            Authority.READ_ACTIVE_USERS,
            Authority.READ_DELETED_USERS,
            Authority.CREATE_USER,
            Authority.UPDATE_INFO,
            Authority.UPDATE_OTHERS,
            Authority.SOFT_DELETE_USER,
            Authority.RESTORE_USER,
            Authority.HARD_DELETE_USER,
            Authority.RESET_PASSWORD,
            Authority.UPDATE_PASSWORD,
            Authority.IMPORT_EXCEL_USER,
            Authority.IMPORT_CSV_USER,

            Authority.READ_CART_ITEM,
            Authority.CREATE_CART_ITEM,
            Authority.UPDATE_CART_ITEM,
            Authority.DELETE_CART_ITEM,

            Authority.CREATE_PAYMENT,

            Authority.READ_ORDER
    )),

    ADMINISTRATOR(Set.of(

    ));

    private final Set<Authority> authorities;
}
