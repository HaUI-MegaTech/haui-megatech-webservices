package shop.haui_megatech.domain.dto.common;

import lombok.Builder;

@Builder
public record RequestIdDTO(
        String token,
        Integer id
) {
}
