package shop.haui_megatech.domain.dto.product;

import lombok.Builder;

@Builder
public record FilterProductRequestDTO(
        String brandIds,
        Float minPrice,
        Float maxPrice
) {
}
