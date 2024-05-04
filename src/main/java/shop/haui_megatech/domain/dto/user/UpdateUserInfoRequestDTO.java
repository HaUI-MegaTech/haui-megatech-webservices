package shop.haui_megatech.domain.dto.user;

public record UpdateUserInfoRequestDTO(
        String firstName,
        String lastName,
        String avatar,
        String email,
        String phoneNumber
) {
}
