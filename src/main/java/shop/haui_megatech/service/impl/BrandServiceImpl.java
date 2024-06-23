package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.brand.BrandResponseDTO;
import shop.haui_megatech.domain.dto.brand.BrandStatisticResponseDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.entity.Brand;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.BrandMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
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
    public GlobalResponseDTO<NoPaginatedMeta, BrandResponseDTO> getOne(Integer id) {
        Optional<Brand> found = brandRepository.findById(id);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BrandResponseDTO>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<PaginatedMeta, List<BrandResponseDTO>> getList(PaginationRequestDTO request) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<Brand> page = brandRepository.findAll(pageable);

        List<Brand> brands = page.getContent();
        return GlobalResponseDTO
                .<PaginatedMeta, List<BrandResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(Pagination
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build()
                )
                .data(brands
                        .parallelStream()
                        .map(BrandMapper.INSTANCE::toBrandResponseDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalRevenue() {
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
                .<NoPaginatedMeta, List<BrandStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalSold() {
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
                .<NoPaginatedMeta, List<BrandStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalView() {
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
                .<NoPaginatedMeta, List<BrandStatisticResponseDTO>>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(data)
                .build();
    }
}
