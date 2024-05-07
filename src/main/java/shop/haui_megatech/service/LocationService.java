package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.LocationDTO;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;

public interface LocationService {
    LocationDTO.Response<Province> getProvinces();

    LocationDTO.Response<District> getDistrictsByProvince(String code);

    LocationDTO.Response<Ward> getWardsByDistrict(String code);
}
