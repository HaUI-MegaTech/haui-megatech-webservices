package shop.haui_megatech.domain.dto.user;

public record CreateUserRequestDTO(
        String firstName,
        String lastName,
        String username,
        String password,
        String confirmPassword
) {
}
