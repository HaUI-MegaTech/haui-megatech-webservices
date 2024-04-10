package shop.haui_megatech.domain.dto.authentication;

import lombok.Builder;

@Builder
public record AuthenticationResponseDTO(
        String token
) {
}
