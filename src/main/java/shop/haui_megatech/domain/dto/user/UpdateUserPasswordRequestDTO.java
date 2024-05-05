package shop.haui_megatech.domain.dto.user;

public record UpdateUserPasswordRequestDTO(
        String oldPassword,
        String newPassword,
        String confirmNewPassword
) {
}
