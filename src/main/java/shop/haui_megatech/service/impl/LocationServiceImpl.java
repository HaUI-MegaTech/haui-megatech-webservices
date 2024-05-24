package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.entity.location.District;
import shop.haui_megatech.domain.entity.location.Province;
import shop.haui_megatech.domain.entity.location.Ward;
import shop.haui_megatech.repository.DistrictRepository;
import shop.haui_megatech.repository.ProvinceRepository;
import shop.haui_megatech.service.LocationService;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final MessageSourceUtil  messageSourceUtil;

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<Province>> getProvinces() {
        return GlobalResponseDTO
                .<NoPaginatedMeta, List<Province>>builder()
                .meta(NoPaginatedMeta.builder()
                                     .message(messageSourceUtil.getMessage(SuccessMessage.Location.FOUND))
                                     .build()
                )
                .data(provinceRepository.findAll())
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<District>> getDistrictsByProvince(String code) {
        return GlobalResponseDTO
                .<NoPaginatedMeta, List<District>>builder()
                .meta(NoPaginatedMeta.builder()
                                     .message(messageSourceUtil.getMessage(SuccessMessage.Location.FOUND))
                                     .build())
                .data(provinceRepository.findById(code).orElseThrow().getDistricts())
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<Ward>> getWardsByDistrict(String code) {
        return GlobalResponseDTO
                .<NoPaginatedMeta, List<Ward>>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .message(messageSourceUtil.getMessage(SuccessMessage.Location.FOUND))
                        .build()
                )
                .data(districtRepository.findById(code).orElseThrow().getWards())
                .build();
    }
}
