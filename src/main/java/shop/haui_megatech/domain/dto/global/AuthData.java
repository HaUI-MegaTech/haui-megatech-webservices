package shop.haui_megatech.domain.dto.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import shop.haui_megatech.domain.dto.user.FullUserResponseDTO;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthData(
        String refreshToken,
        String accessToken,
        FullUserResponseDTO loggedInUser
) {
}
