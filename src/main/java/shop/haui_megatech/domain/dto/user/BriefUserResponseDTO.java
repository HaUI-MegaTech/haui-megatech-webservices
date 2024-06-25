package shop.haui_megatech.domain.dto.user;

import lombok.Builder;
import shop.haui_megatech.domain.entity.enums.Gender;
import shop.haui_megatech.domain.entity.enums.Role;

@Builder
public record BriefUserResponseDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String avatarImageUrl,
        String phoneNumber,
        Role role
) {}
