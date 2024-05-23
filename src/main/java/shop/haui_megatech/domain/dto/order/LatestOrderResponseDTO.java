package shop.haui_megatech.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import shop.haui_megatech.constant.DatetimeFormat;
import shop.haui_megatech.domain.entity.enums.OrderStatus;

import java.util.Date;

@Builder
public record LatestOrderResponseDTO(
        Integer id,
        String customer,
        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = DatetimeFormat.INDOCHINA_FULL_DATETIME_FORMAT,
                timezone = DatetimeFormat.VIETNAM_TIMEZONE
        )
        Date orderTime,
        Double total,
        OrderStatus status
) {
}
