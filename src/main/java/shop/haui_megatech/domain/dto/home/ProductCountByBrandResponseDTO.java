package shop.haui_megatech.domain.dto.home;

import lombok.Builder;

@Builder
public record ProductCountByBrandResponseDTO(
        String name,
        Integer count
) {
}
