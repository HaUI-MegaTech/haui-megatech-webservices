package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.location.LocationResponseDTO;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;

public interface LocationService {
    LocationResponseDTO<Province> getProvinces();

    LocationResponseDTO<District> getDistrictsByProvince(String code);

    LocationResponseDTO<Ward> getWardsByDistrict(String code);
}
