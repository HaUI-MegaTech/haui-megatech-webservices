package shop.haui_megatech.domain.dto.cart;

public record CartItemDTO(
        Integer cartItemId,
        ProductCartDTO product,
        Integer quantity
) {
}
