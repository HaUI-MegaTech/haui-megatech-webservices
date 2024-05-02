package shop.haui_megatech.domain.dto.image;

import lombok.Builder;

@Builder
public record ImageDTO(
        Integer id,
        String link
) {
}
