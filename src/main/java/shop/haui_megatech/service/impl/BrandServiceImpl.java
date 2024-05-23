package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.brand.BrandResponseDTO;
import shop.haui_megatech.domain.dto.brand.BrandStatisticResponseDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.MetaDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.global.Status;
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
    public GlobalResponseDTO<?> getOne(Integer id) {
        Optional<Brand> found = brandRepository.findById(id);

        return GlobalResponseDTO
                .<BrandResponseDTO>builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Brand.FOUND))
                        .build()
                )
                .data(BrandMapper.INSTANCE.toBrandResponseDTO(found.orElseThrow(
                                () -> new NotFoundException(ErrorMessage.Brand.NOT_FOUND))
                        )
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<List<BrandResponseDTO>> getList(PaginationRequestDTO request) {
        List<Brand> brands = brandRepository.findAll();
        return GlobalResponseDTO
                .<List<BrandResponseDTO>>builder()
                .data(brands
                        .parallelStream()
                        .map(BrandMapper.INSTANCE::toBrandResponseDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<List<BrandStatisticResponseDTO>> getTotalRevenue() {
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

        return GlobalResponseDTO
                .<List<BrandStatisticResponseDTO>>builder()
                .meta(MetaDTO.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }

    @Override
    public GlobalResponseDTO<List<BrandStatisticResponseDTO>> getTotalSold() {
        List<Brand> list = brandRepository.findAll();
        List<BrandStatisticResponseDTO> data =
                list.parallelStream()
                    .map(brand -> BrandStatisticResponseDTO
                            .builder()
                            .name(brand.getName())
                            .value(brand.getProducts()
                                        .parallelStream()
                                        .mapToLong(product -> product.getTotalSold())
                                        .sum()
                            )
                            .build()
                    )
                    .toList();

        return GlobalResponseDTO
                .<List<BrandStatisticResponseDTO>>builder()
                .meta(MetaDTO.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }

    @Override
    public GlobalResponseDTO<List<BrandStatisticResponseDTO>> getTotalView() {
        List<Brand> list = brandRepository.findAll();
        List<BrandStatisticResponseDTO> data =
                list.parallelStream()
                    .map(brand -> BrandStatisticResponseDTO
                            .builder()
                            .name(brand.getName())
                            .value(brand.getProducts()
                                        .parallelStream()
                                        .mapToLong(product -> product.getTotalViews())
                                        .sum()
                            )
                            .build()
                    )
                    .toList();

        return GlobalResponseDTO
                .<List<BrandStatisticResponseDTO>>builder()
                .meta(MetaDTO.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }
}
