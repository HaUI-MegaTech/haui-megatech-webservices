package shop.haui_megatech.domain.dto.cart;

public record CartItemDTO(
        Integer id,
        ProductCartDTO product,
        Integer quantity
) {
}
