package shop.haui_megatech.domain.dto.user;

import org.springframework.web.multipart.MultipartFile;

public record UpdateUserInfoRequest(
        String firstName,
        String lastName,
        MultipartFile avatar,
        String email,
        String phoneNumber
) {
}
