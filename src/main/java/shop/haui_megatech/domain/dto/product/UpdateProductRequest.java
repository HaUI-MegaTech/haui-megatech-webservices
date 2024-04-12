package shop.haui_megatech.domain.dto.product;

public record UpdateProductRequest(
        String name,
        Float price
) {
}
