package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.LocationDTO;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;
import shop.haui_megatech.repository.DistrictRepository;
import shop.haui_megatech.repository.ProvinceRepository;
import shop.haui_megatech.service.LocationService;
import shop.haui_megatech.utility.MessageSourceUtil;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final MessageSourceUtil  messageSourceUtil;

    @Override
    public LocationDTO.Response<Province> getProvinces() {
        return LocationDTO.Response
                .<Province>builder()
                .message(messageSourceUtil.getMessage(SuccessMessage.Location.FOUND))
                .items(provinceRepository.findAll())
                .build();
    }

    @Override
    public LocationDTO.Response<District> getDistrictsByProvince(String code) {
        return LocationDTO.Response
                .<District>builder()
                .message(messageSourceUtil.getMessage(SuccessMessage.Location.FOUND))
                .items(provinceRepository.findById(code).orElseThrow().getDistricts())
                .build();
    }

    @Override
    public LocationDTO.Response<Ward> getWardsByDistrict(String code) {
        return LocationDTO.Response
                .<Ward>builder()
                .message(messageSourceUtil.getMessage(SuccessMessage.Location.FOUND))
                .items(districtRepository.findById(code).orElseThrow().getWards())
                .build();
    }
}
