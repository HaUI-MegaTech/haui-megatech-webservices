package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.brand.BrandResponseDTO;
import shop.haui_megatech.domain.dto.brand.BrandStatisticResponseDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
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

        return CommonResponseDTO
                .<BrandResponseDTO>builder()
                .success(true)
                .message(messageSourceUtil.getMessage(SuccessMessage.Brand.FOUND))
                .item(BrandMapper.INSTANCE.toBrandResponseDTO(found.orElseThrow(
                                () -> new NotFoundException(ErrorMessage.Brand.NOT_FOUND))
                        )
                )
                .build();
    }

    @Override
    public PaginationResponseDTO<BrandResponseDTO> getList(PaginationRequestDTO request) {
        List<Brand> brands = brandRepository.findAll();
        return PaginationResponseDTO
                .<BrandResponseDTO>builder()
                .items(brands
                        .parallelStream()
                        .map(BrandMapper.INSTANCE::toBrandResponseDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public NoPaginationResponseDTO<BrandStatisticResponseDTO> getTotalRevenue() {
        List<Brand> list = brandRepository.findAll();
        List<BrandStatisticResponseDTO> data =
                list.parallelStream()
                    .map(brand -> BrandStatisticResponseDTO
                            .builder()
                            .name(brand.getName())
                            .value(brand.getProducts()
                                        .parallelStream()
                                        .mapToLong(product ->
                                                (long) (product.getTotalSold() * (product.getCurrentPrice() - product.getImportPrice()))
                                        )
                                        .sum()
                            )
                            .build()
                    )
                    .toList();

        return NoPaginationResponseDTO
                .<BrandStatisticResponseDTO>builder()
                .success(true)
                .items(data)
                .build();
    }
}
