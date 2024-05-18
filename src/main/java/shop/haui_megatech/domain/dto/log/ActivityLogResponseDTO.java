package shop.haui_megatech.domain.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import shop.haui_megatech.constant.DatetimeFormat;
import shop.haui_megatech.domain.entity.User;

import java.util.Date;

@Builder
public record ActivityLogResponseDTO(
        Integer id,
        String subject,
        String operation,
        Boolean success,

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date whenCreated
) {
}
