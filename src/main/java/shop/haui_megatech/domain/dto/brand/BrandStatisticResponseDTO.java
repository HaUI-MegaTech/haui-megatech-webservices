package shop.haui_megatech.domain.dto.brand;

import lombok.Builder;

@Builder
public record BrandStatisticResponseDTO(
        String name,
        Long value
) {
}
