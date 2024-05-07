package shop.haui_megatech.domain.dto.user;

import lombok.Builder;
import shop.haui_megatech.domain.entity.Gender;

@Builder
public record UserDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String avatarImageUrl,
        String phoneNumber
) {
}
