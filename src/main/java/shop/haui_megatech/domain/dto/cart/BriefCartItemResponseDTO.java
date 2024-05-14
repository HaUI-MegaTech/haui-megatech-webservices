package shop.haui_megatech.domain.dto.cart;

public record BriefCartItemResponseDTO(
        Integer id,
        ProductInCartItemResponseDTO product,
        Integer quantity
) {
}
