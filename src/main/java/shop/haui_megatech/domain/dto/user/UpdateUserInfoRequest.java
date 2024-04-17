package shop.haui_megatech.domain.dto.user;

public record UpdateUserInfoRequest(
        String firstName,
        String lastName,
        String avatar,
        String email,
        String phoneNumber
) {
}
