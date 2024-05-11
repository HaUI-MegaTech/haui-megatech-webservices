package shop.haui_megatech.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.haui_megatech.constant.DatetimeFormat;

import java.util.Date;

public record FeedbackDTO() {
    public record AddRequest(
            String alias,
            String content,
            Byte rating,
            Integer productId
    ) {}

    public record UpdateRequest(
            String alias,
            String content,
            Byte rating
    ) {}

    public record UserResponse(
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
            ProductDTO.MinimizedResponse product
    ) {}

    public record ProductResponse(
            Integer id,
            String alias,
            String content,
            Byte rating,
            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                    timezone = DatetimeFormat.VIETNAM_TIMEZONE
            )
            Date whenCreated
    ) {}
}
