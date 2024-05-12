package shop.haui_megatech.domain.dto.product;

public record FilterProductRequestDTO(
        String brandIds,
        Float minPrice,
        Float maxPrice
) {
}
