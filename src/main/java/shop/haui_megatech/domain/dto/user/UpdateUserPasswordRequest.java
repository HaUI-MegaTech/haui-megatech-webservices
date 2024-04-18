package shop.haui_megatech.domain.dto.user;

import lombok.NonNull;

public record UpdateUserPasswordRequest(
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
