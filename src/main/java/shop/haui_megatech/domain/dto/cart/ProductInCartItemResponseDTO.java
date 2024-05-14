package shop.haui_megatech.domain.dto.cart;

public record ProductInCartItemResponseDTO(
        Integer id,
        String mainImageUrl,
        String name,
        Float oldPrice,
        Float currentPrice
) {
}
