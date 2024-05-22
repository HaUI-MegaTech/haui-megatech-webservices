package shop.haui_megatech.domain.dto.home;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.haui_megatech.constant.DatetimeFormat;

import java.util.Date;

public record LoginStatisticResponseDTO(
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date date,
        Integer loggedIn
) {
}
