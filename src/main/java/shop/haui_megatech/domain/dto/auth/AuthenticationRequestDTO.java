package shop.haui_megatech.domain.dto.auth;

public record AuthenticationRequestDTO(
        String username,
        String password
) {
}
