package shop.haui_megatech.domain.dto.cart;

public record ProductCartDTO(
        String mainImageUrl,
        String name,
        Float oldPrice,
        Float currentPrice
) {
}
