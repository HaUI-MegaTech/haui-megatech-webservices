package shop.haui_megatech.domain.dto.user;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import shop.haui_megatech.constant.ErrorMessageConstant;

@Builder
public record AddUserRequestDTO(
        @Size(min = 4, message = ErrorMessageConstant.User.DEFICIENT_USERNAME_LENGTH)
        String username,
        String password,
        String confirmPassword,

        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
