package shop.haui_megatech.domain.dto.product;

public record ProductFilterRequestDTO(
        String brandIds,
        Float minPrice,
        Float maxPrice
) {
}
