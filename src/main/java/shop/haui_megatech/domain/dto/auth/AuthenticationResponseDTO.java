package shop.haui_megatech.domain.dto.auth;

import lombok.Builder;
import shop.haui_megatech.domain.dto.user.FullUserResponseDTO;

@Builder
public record AuthenticationResponseDTO(
        Boolean success,
        String message,
        String token,
        FullUserResponseDTO loggedInUser
) {
}
