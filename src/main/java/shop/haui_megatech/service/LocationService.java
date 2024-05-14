package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;

public interface LocationService {
    NoPaginationResponseDTO<Province> getProvinces();

    NoPaginationResponseDTO<District> getDistrictsByProvince(String code);

    NoPaginationResponseDTO<Ward> getWardsByDistrict(String code);
}
