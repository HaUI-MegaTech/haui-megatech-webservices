package shop.haui_megatech.domain.dto;

import lombok.Builder;

@Builder
public record ProductDTO(
        Integer id,
        String name,
        float price
) {
}
