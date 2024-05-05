package shop.haui_megatech.domain.dto.cart;

public record CartItemRequestDTO(
        Integer productId,
        Integer quantity
) {
}
