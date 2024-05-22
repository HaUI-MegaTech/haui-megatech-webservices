package shop.haui_megatech.domain.dto.address;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.haui_megatech.constant.DatetimeFormat;

import java.util.Date;

public record AddressResponseDTO(
        Integer id,
        String province,
        String provinceCode,
        String district,
        String districtCode,
        String ward,
        String wardCode,
        String detail,
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
        Date lastUpdated
) {}
