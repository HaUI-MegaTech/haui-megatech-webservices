package shop.haui_megatech.domain.dto.cart;

public record ModifyCartItemRequestDTO(
        Integer cartItemId,
        Integer productId,
        Integer quantity
) {
}
