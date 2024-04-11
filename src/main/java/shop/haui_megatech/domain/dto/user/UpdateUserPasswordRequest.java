package shop.haui_megatech.domain.dto.user;

import lombok.NonNull;

public record UpdateUserPasswordRequest(
        @NonNull
        String oldPassword,

        @NonNull
        String newPassword,

        @NonNull
        String confirmNewPassword
) {
}
