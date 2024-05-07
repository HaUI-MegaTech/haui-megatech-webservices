package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.domain.dto.location.LocationResponseDTO;
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
    public LocationResponseDTO<Province> getProvinces() {
        return LocationResponseDTO
                .<Province>builder()
                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Location.FOUND))
                .items(provinceRepository.findAll())
                .build();
    }

    @Override
    public LocationResponseDTO<District> getDistrictsByProvince(String code) {
        return LocationResponseDTO
                .<District>builder()
                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Location.FOUND))
                .items(provinceRepository.findById(code).orElseThrow().getDistricts())
                .build();
    }

    @Override
    public LocationResponseDTO<Ward> getWardsByDistrict(String code) {
        return LocationResponseDTO
                .<Ward>builder()
                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Location.FOUND))
                .items(districtRepository.findById(code).orElseThrow().getWards())
                .build();
    }
}
