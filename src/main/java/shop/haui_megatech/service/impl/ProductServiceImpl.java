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
import shop.haui_megatech.domain.dto.common.ImportDataRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.product.*;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.mapper.ProductMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.MalformedFileException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.service.ProductService;
import shop.haui_megatech.utility.CsvUtil;
import shop.haui_megatech.utility.ExcelUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public GlobalResponseDTO<FullProductResponseDTO> getOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        return GlobalResponseDTO
                .<FullProductResponseDTO>builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.FOUND))
                        .build()
                )
                .data(ProductMapper.INSTANCE.toFullProductResponseDTO(found.get()))
                .build();

    }

    @Override
    public GlobalResponseDTO<List<BriefProductResponseDTO>> getList(
            PaginationRequestDTO request,
            FilterProductRequestDTO filter
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        if (request.keyword() != null) {
            String[] keywords = request.keyword().split(",");
            List<Product> products = new ArrayList<>();
            int pageCount = 0;

            if (filter != null) {
                if (filter.brandIds() != null) {
                    List<Integer> brandIds = Arrays.stream(filter.brandIds().split(","))
                                                   .map(String::trim)
                                                   .map(Integer::valueOf)
                                                   .toList();
                    if (filter.minPrice() != null && filter.maxPrice() != null) {
                        for (String keyword : keywords) {
                            ++pageCount;
                            Page<Product> page = productRepository.filterActiveListByKeywordAndBrandIdsAndPrice(
                                    keyword,
                                    brandIds,
                                    filter.minPrice(),
                                    filter.maxPrice(),
                                    pageable
                            );
                            products.addAll(page.getContent());
                        }
                    } else {
                        for (String keyword : keywords) {
                            ++pageCount;
                            Page<Product> page = productRepository.filterActiveListByKeywordAndBrandIds(
                                    keyword,
                                    brandIds,
                                    pageable
                            );
                            products.addAll(page.getContent());
                        }
                    }
                } else {
                    if (filter.minPrice() != null && filter.maxPrice() != null) {
                        for (String keyword : keywords) {
                            ++pageCount;
                            Page<Product> page = productRepository.filterActiveListByKeywordAndPrice(
                                    keyword,
                                    filter.minPrice(),
                                    filter.maxPrice(),
                                    pageable
                            );
                            products.addAll(page.getContent());
                        }
                    } else {
                        for (String keyword : keywords) {
                            ++pageCount;
                            Page<Product> page = productRepository.searchActiveProductsPage(
                                    keyword,
                                    pageable
                            );
                            products.addAll(page.getContent());
                        }
                    }
                }
            } else {
                for (String keyword : keywords) {
                    ++pageCount;
                    Page<Product> page = productRepository.searchActiveProductsPage(
                            keyword,
                            pageable
                    );
                    products.addAll(page.getContent());
                }
            }

            return GlobalResponseDTO
                    .<List<BriefProductResponseDTO>>builder()
                    .meta(MetaDTO
                            .builder()
                            .pagination(PaginationDTO
                                    .builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.index())
                                    .pageSize(request.limit())
                                    .totalItems((long) products.size())
                                    .totalPages(pageCount)
                                    .build())
                            .build())
                    .data(products.parallelStream()
                                  .map(ProductMapper.INSTANCE::toBriefProductResponseDTO)
                                  .collect(Collectors.toList()))
                    .build();
        }


        Page<Product> page;

        if (filter != null) {
            if (filter.brandIds() != null) {
                List<Integer> brandIds = Arrays.stream(filter.brandIds().split(","))
                                               .map(String::trim)
                                               .map(Integer::valueOf)
                                               .toList();
                if (filter.minPrice() != null && filter.maxPrice() != null) {
                    page = productRepository.filterActiveListByPriceAndBrandIds(
                            brandIds,
                            filter.minPrice(),
                            filter.maxPrice(),
                            pageable
                    );
                } else {
                    page = productRepository.filterActiveListByBrandIds(
                            brandIds,
                            pageable
                    );
                }
            } else {
                if (filter.minPrice() != null && filter.maxPrice() != null) {
                    page = productRepository.filterActiveListByPrice(
                            filter.minPrice(),
                            filter.maxPrice(),
                            pageable
                    );
                } else {
                    page = productRepository.getActiveProductsPage(pageable);
                }
            }
        } else {
            page = productRepository.getActiveProductsPage(pageable);
        }

        List<Product> products = page.getContent();

        return GlobalResponseDTO
                .<List<BriefProductResponseDTO>>builder()
                .meta(MetaDTO
                        .builder()
                        .pagination(PaginationDTO
                                .builder()
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())

                        .build())
                .data(products.parallelStream()
                              .map(ProductMapper.INSTANCE::toBriefProductResponseDTO)
                              .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GlobalResponseDTO<BriefProductResponseDTO> addOne(AddProductRequestDTO request) {

        return GlobalResponseDTO
                .<BriefProductResponseDTO>builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.ADDED_ONE))
                        .build()
                )
                .data(ProductMapper.INSTANCE.toBriefProductResponseDTO(
                        productRepository.save(ProductMapper.INSTANCE.toProduct(request))
                ))
                .build();
    }

    @Override
    public GlobalResponseDTO<?> importExcel(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<Product> products = ExcelUtil.excelToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(products);
            return GlobalResponseDTO
                    .builder()
                    .meta(MetaDTO
                            .builder()
                            .status(Status.SUCCESS)
                            .message(messageSourceUtil.getMessage(
                                    SuccessMessage.Product.IMPORTED_LIST,
                                    savedProducts.size()
                            ))
                            .build()
                    )
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(ErrorMessage.Import.PROCESS_EXCEL);
        }
    }

    @Override
    public GlobalResponseDTO<?> importCsv(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<Product> products = CsvUtil.csvToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(products);
            return GlobalResponseDTO
                    .builder()
                    .meta(MetaDTO
                            .builder()
                            .status(Status.SUCCESS)
                            .message(messageSourceUtil.getMessage(
                                    SuccessMessage.Product.IMPORTED_LIST,
                                    savedProducts.size()
                            ))
                            .build()
                    )
                    .build();
        } catch (IOException ex) {
            throw new RuntimeException(ErrorMessage.Import.PROCESS_CSV);
        }
    }

    @Override
    public GlobalResponseDTO<?> updateOne(
            Integer id,
            UpdateProductRequestDTO request
    ) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();

        if (request.name() != null) foundProduct.setName(request.name());
        if (request.price() != null) foundProduct.setCurrentPrice(request.price());
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(SuccessMessage.Product.UPDATED_ONE)
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> hardDeleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        productRepository.deleteById(id);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(SuccessMessage.Product.HARD_DELETED_ONE)
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        productRepository.deleteAllById(request.ids());

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.Product.HARD_DELETED_LIST,
                                request.ids().size()
                        ))
                        .build()
                )
                .build();
    }


    @Override
    public GlobalResponseDTO<?> updateListFromExcel(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<Product> updatedProducts = ExcelUtil.excelToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(updatedProducts);
            return GlobalResponseDTO
                    .builder()
                    .meta(MetaDTO
                            .builder()
                            .status(Status.SUCCESS)
                            .message(messageSourceUtil.getMessage(
                                    SuccessMessage.Product.UPDATED_LIST_FROM_EXCEL,
                                    savedProducts.size()
                            ))
                            .build()
                    )
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Excel data is failed to store: " + e.getMessage());
        }
    }


    @Override
    public GlobalResponseDTO<?> hideOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        productRepository.deleteById(id);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(SuccessMessage.Product.HIDED_ONE)
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> hideList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setHidden(true));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.Product.HIDED_LIST,
                                foundProducts.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<List<BriefProductResponseDTO>> getHiddenList(PaginationRequestDTO request) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<Product> page = request.keyword() == null
                             ? productRepository.getHiddenProductsPage(pageable)
                             : productRepository.searchHiddenProductsPage(request.keyword(), pageable);

        List<Product> products = page.getContent();

        return GlobalResponseDTO
                .<List<BriefProductResponseDTO>>builder()
                .meta(MetaDTO
                        .builder()
                        .pagination(PaginationDTO
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build()
                        )
                        .build()
                )
                .data(products.parallelStream()
                              .map(ProductMapper.INSTANCE::toBriefProductResponseDTO)
                              .collect(Collectors.toList()))
                .build();
    }

    @Override
    public GlobalResponseDTO<?> restoreOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setDeleted(false);
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.RESTORED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> restoreList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setDeleted(false));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.Product.RESTORED_LIST,
                                foundProducts.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> exposeOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setHidden(false);
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.EXPOSED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> exposeList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setHidden(false));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.Product.EXPOSED_LIST,
                                foundProducts.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> softDeleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setDeleted(true);
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.SOFT_DELETED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> softDeleteList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setDeleted(true));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(
                                SuccessMessage.Product.SOFT_DELETED_LIST,
                                foundProducts.size()
                        ))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<List<BriefProductResponseDTO>> getDeletedList(PaginationRequestDTO request) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<Product> page = request.keyword() == null
                             ? productRepository.getDeletedProductsPage(pageable)
                             : productRepository.searchDeletedProductsPage(request.keyword(), pageable);

        List<Product> products = page.getContent();

        return GlobalResponseDTO
                .<List<BriefProductResponseDTO>>builder()
                .meta(MetaDTO
                        .builder()
                        .pagination(PaginationDTO
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build())
                .data(products.parallelStream()
                              .map(ProductMapper.INSTANCE::toBriefProductResponseDTO)
                              .collect(Collectors.toList()))
                .build();
    }
}
