package shop.haui_megatech.domain.dto.user;

import lombok.Builder;

@Builder
public record CreateUserRequestDTO(
        String firstName,
        String lastName,
        String username,
        String password,
        String confirmPassword
) {
}
