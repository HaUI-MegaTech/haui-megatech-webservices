package shop.haui_megatech.domain.dto;

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
            String detail
    ) {}

}
