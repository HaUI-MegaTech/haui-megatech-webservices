package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.BrandDTO;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.entity.Brand;
import shop.haui_megatech.domain.mapper.BrandMapper;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.BrandRepository;
import shop.haui_megatech.service.BrandService;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository   brandRepository;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public CommonResponseDTO<?> getOne(Integer id) {
        Optional<Brand> found = brandRepository.findById(id);

        return CommonResponseDTO.<BrandDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.Brand.FOUND))
                                .item(BrandMapper.INSTANCE.toBrandDTO(found.orElseThrow(
                                              () -> new NotFoundException(ErrorMessage.Brand.NOT_FOUND))
                                      )
                                )
                                .build();
    }

    @Override
    public PaginationDTO.Response<BrandDTO> getList(PaginationDTO.Request request) {
        List<Brand> brands = brandRepository.findAll();
        return PaginationDTO.Response.<BrandDTO>builder()
                                     .items(brands.parallelStream()
                                                  .map(BrandMapper.INSTANCE::toBrandDTO)
                                                  .collect(Collectors.toList()))
                                     .build();
    }
}
