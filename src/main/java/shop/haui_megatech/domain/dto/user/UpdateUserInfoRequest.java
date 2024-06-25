package shop.haui_megatech.domain.dto.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.domain.entity.enums.Role;

public record UpdateUserInfoRequest(
        @RequestParam(name = "firstName", required = false) String firstName,
        @RequestParam(name = "lastName", required = false) String lastName,
        @RequestParam(name = "avatar", required = false) MultipartFile avatar,
        @RequestParam(name = "email", required = false) String email,
        @RequestParam(name = "phoneNumber", required = false) String phoneNumber,
        @RequestParam(name = "role", required = false) String role
) {
}
