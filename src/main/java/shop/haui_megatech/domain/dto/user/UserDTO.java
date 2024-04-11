package shop.haui_megatech.domain.dto.user;

import lombok.Builder;

@Builder
public record UserDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        String avatar,
        String email
) {
}
