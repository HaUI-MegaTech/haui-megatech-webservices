package shop.haui_megatech.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.constant.DatetimeFormat;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.domain.entity.Address;
import shop.haui_megatech.domain.entity.Gender;

import java.util.Date;
import java.util.List;

public record UserDTO() {

    @Builder
    public record Response(
            Integer id,
            String username,
            String firstName,
            String lastName,
            Gender gender,
            String email,
            String avatarImageUrl,
            String phoneNumber
    ) {}

    @Builder
    public record DetailResponse(
            Integer id,
            String username,
            String firstName,
            String lastName,
            Gender gender,
            String email,
            String avatarImageUrl,
            String phoneNumber,
            List<Address> addresses,

            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                    timezone = DatetimeFormat.VIETNAM_TIMEZONE
            )
            Date lastUpdated,

            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                    timezone = DatetimeFormat.VIETNAM_TIMEZONE
            )
            Date whenCreated,

            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                    timezone = DatetimeFormat.VIETNAM_TIMEZONE
            )
            Date lastLogined,
            Integer logined
    ) {}

    public record AddRequest(
            @Size(min = 4, message = ErrorMessage.User.DEFICIENT_USERNAME_LENGTH)
            String username,
            String password,
            String confirmPassword,

            String firstName,
            String lastName,
            String email,
            String phoneNumber
    ) {}

    public record UpdatePasswordRequest(
            String oldPassword,
            String newPassword,
            String confirmNewPassword
    ) {}

    public record UpdateInfoRequest(
            String firstName,
            String lastName,
            MultipartFile avatar,
            String email,
            String phoneNumber
    ) {}
}
