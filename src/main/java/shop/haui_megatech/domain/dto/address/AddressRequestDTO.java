package shop.haui_megatech.domain.dto.address;

public record AddressRequestDTO(
        String province,
        String provinceCode,
        String district,
        String districtCode,
        String ward,
        String wardCode,
        String detail
) {}
