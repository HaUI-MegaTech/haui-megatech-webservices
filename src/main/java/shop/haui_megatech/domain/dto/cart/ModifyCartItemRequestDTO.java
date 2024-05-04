package shop.haui_megatech.domain.dto.cart;

public record ModifyCartItemRequestDTO(
        String token,
        Integer cartItemId,
        Integer productId,
        Integer quantity
) {
}
