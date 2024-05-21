package shop.haui_megatech.domain.dto.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import shop.haui_megatech.domain.dto.user.FullUserResponseDTO;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthenticationResponseDTO(
        Boolean success,
        String message,
        String refreshToken,
        String accessToken,
        FullUserResponseDTO loggedInUser
) {
}
