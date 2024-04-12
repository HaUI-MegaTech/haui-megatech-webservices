package shop.haui_megatech.domain.dto.product;

public record CreateProductRequest(
        String name,
        Float price
) {
}
