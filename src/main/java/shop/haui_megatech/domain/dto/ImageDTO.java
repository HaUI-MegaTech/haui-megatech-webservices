package shop.haui_megatech.domain.dto;

import lombok.Builder;

@Builder
public record ImageDTO(
        Integer id,
        String url
) {
}
