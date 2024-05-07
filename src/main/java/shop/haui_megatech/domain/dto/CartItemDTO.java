package shop.haui_megatech.domain.dto;

public record CartItemDTO() {
    public record Response(
            Integer id,
            CartItemDTO.Product product,
            Integer quantity
    ) {}

    public record Request(
            Integer productId,
            Integer quantity
    ) {}

    public record Product(
            Integer id,
            String mainImageUrl,
            String name,
            Float oldPrice,
            Float currentPrice
    ) {}
}
