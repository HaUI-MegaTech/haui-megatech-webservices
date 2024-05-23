package shop.haui_megatech.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import shop.haui_megatech.constant.DatetimeFormat;
import shop.haui_megatech.domain.entity.enums.Gender;
import shop.haui_megatech.domain.entity.enums.Role;

import java.util.Date;
import java.util.List;

@Builder
public record FullUserResponseDTO(
        Integer id,
        String username,
        String firstName,
        String lastName,
        Gender gender,
        String email,
        String avatarImageUrl,
        String phoneNumber,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_FULL_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date lastUpdated,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_FULL_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date whenCreated,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_FULL_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date lastLoggedIn,
        Integer loggedIn,
        Role role,
        List<GrantedAuthority> authorities
) {
}
