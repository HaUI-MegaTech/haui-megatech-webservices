package shop.haui_megatech.domain.dto.feedback;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.haui_megatech.constant.DatetimeFormat;

import java.util.Date;

public record BriefFeedbackForUser(
        Integer id,
        String alias,
        String content,
        Byte rating,
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date whenCreated,
        ProductInFeedbackDTO product
) {
}
