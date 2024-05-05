package shop.haui_megatech.domain.dto.cart;

public record ProductCartDTO(
        Integer id,
        String mainImageUrl,
        String name,
        Float oldPrice,
        Float currentPrice
) {
}
