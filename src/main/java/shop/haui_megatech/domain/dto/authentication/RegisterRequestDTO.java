package shop.haui_megatech.domain.dto.authentication;

public record RegisterRequestDTO(
        String firstName,
        String lastName,
        String username,
        String password
) {
}
