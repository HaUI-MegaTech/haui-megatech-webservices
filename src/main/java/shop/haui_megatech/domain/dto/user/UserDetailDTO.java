package shop.haui_megatech.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import shop.haui_megatech.domain.entity.Address;
import shop.haui_megatech.domain.entity.Gender;

import java.util.Date;
import java.util.List;

@Builder
public record UserDetailDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String avatarImageUrl,
        String phoneNumber,
        List<Address> addresses,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
        Date lastUpdated,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
        Date whenCreated,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
        Date lastLogined,
        Integer logined,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
        Date whenDeleted,
        Boolean deleted
) {
}
