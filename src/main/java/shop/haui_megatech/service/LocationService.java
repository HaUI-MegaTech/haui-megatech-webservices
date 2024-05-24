package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;

import java.util.List;

public interface LocationService {
    GlobalResponseDTO<NoPaginatedMeta, List<Province>> getProvinces();

    GlobalResponseDTO<NoPaginatedMeta, List<District>> getDistrictsByProvince(String code);

    GlobalResponseDTO<NoPaginatedMeta, List<Ward>> getWardsByDistrict(String code);
}
