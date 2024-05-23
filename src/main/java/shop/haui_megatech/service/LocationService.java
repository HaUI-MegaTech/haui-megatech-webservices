package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;

import java.util.List;

public interface LocationService {
    GlobalResponseDTO<List<Province>> getProvinces();

    GlobalResponseDTO<List<District>> getDistrictsByProvince(String code);

    GlobalResponseDTO<List<Ward>> getWardsByDistrict(String code);
}
