package shop.haui_megatech.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import shop.haui_megatech.constant.DatetimeFormat;

import java.util.Date;

public record AddressDTO() {

    public record Request(
            String province,
            String provinceCode,
            String district,
            String districtCode,
            String ward,
            String wardCode,
            String detail
    ) {}

    public record Response(
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
                    pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                    timezone = DatetimeFormat.VIETNAM_TIMEZONE
            )
            Date whenCreated,
            @JsonFormat(
                    shape = JsonFormat.Shape.STRING,
                    pattern = DatetimeFormat.INDOCHINA_DATETIME_FORMAT,
                    timezone = DatetimeFormat.VIETNAM_TIMEZONE
            )
            Date lastUpdated
    ) {}

}
