package shop.haui_megatech.domain.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import shop.haui_megatech.constant.ErrorMessage;

public record AddUserRequestDTO(
        @Size(min = 4, message = ErrorMessage.User.DEFICIENT_USERNAME_LENGTH)
        String username,
        String password,
        String confirmPassword,

        String firstName,
        String lastName,

        @NotNull(message = ErrorMessage.User.MISSING_EMAIL_FIELD)
        String email
//        String phoneNumber
) {
}
