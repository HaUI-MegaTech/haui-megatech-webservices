package shop.haui_megatech.domain.dto.user;

public record UpdateUserInfoRequest(
        String firstName,
        String lastName,
//        MultipartFile avatar,
        String email,
        String phoneNumber
) {
}
