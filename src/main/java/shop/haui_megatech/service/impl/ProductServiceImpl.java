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
    public GlobalResponseDTO<NoPaginatedMeta, FullProductResponseDTO> getOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        return GlobalResponseDTO
                .<NoPaginatedMeta, FullProductResponseDTO>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.FOUND))
                        .build()
                )
                .data(ProductMapper.INSTANCE.toFullProductResponseDTO(found.get()))
                .build();

    }

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getList(
            PaginationRequestDTO request,
            FilterProductRequestDTO filter
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);
        String[] fields = Arrays.stream(request.fields().split(","))
                                .map(String::trim)
                                .toArray(String[]::new);
        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(fields)
                          .ascending()
                    : Sort.by(fields)
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
                    .<PaginatedMeta, List<BriefProductResponseDTO>>builder()
                    .meta(PaginatedMeta
                            .builder()
                            .status(Status.SUCCESS)
                            .pagination(Pagination
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
                .<PaginatedMeta, List<BriefProductResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(Pagination
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
    public GlobalResponseDTO<NoPaginatedMeta, BriefProductResponseDTO> addOne(AddProductRequestDTO request) {

        return GlobalResponseDTO
                .<NoPaginatedMeta, BriefProductResponseDTO>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> importExcel(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<Product> products = ExcelUtil.excelToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(products);
            return GlobalResponseDTO
                    .<NoPaginatedMeta, BlankData>builder()
                    .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> importCsv(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<Product> products = CsvUtil.csvToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(products);
            return GlobalResponseDTO
                    .<NoPaginatedMeta, BlankData>builder()
                    .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(
            Integer id,
            UpdateProductRequestDTO request
    ) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();

        if (request.getName() != null) {
            foundProduct.setName(request.getName());
        }

        if (request.getOldPrice() != null) {
            foundProduct.setOldPrice(request.getOldPrice());
        }

        if (request.getCurrentPrice() != null) {
            foundProduct.setCurrentPrice(request.getCurrentPrice());
        }

        if (request.getDiscountPercent() != null) {
            foundProduct.setDiscountPercent(request.getDiscountPercent());
        }

        if (request.getRemaining() != null) {
            foundProduct.setRemaining(request.getRemaining());
        }

        if (request.getProcessor() != null) {
            foundProduct.setProcessor(request.getProcessor());
        }

        if (request.getCores() != null) {
            foundProduct.setCores(request.getCores());
        }

        if (request.getThreads() != null) {
            foundProduct.setThreads(request.getThreads());
        }

        if (request.getFrequency() != null) {
            foundProduct.setFrequency(request.getFrequency());
        }

        if (request.getBoostFrequency() != null) {
            foundProduct.setBoostFrequency(request.getBoostFrequency());
        }

        if (request.getCacheCapacity() != null) {
            foundProduct.setCacheCapacity(request.getCacheCapacity());
        }

        if (request.getMemoryCapacity() != null) {
            foundProduct.setMemoryCapacity(request.getMemoryCapacity());
        }

        if (request.getMemoryType() != null) {
            foundProduct.setMemoryType(request.getMemoryType());
        }

        if (request.getMemoryBus() != null) {
            foundProduct.setMemoryBus(request.getMemoryBus());
        }

        if (request.getMaxMemoryCapacity() != null) {
            foundProduct.setMaxMemoryCapacity(request.getMaxMemoryCapacity());
        }

        if (request.getStorage() != null) {
            foundProduct.setStorage(request.getStorage());
        }

        if (request.getDisplaySize() != null) {
            foundProduct.setDisplaySize(request.getDisplaySize());
        }

        if (request.getResolution() != null) {
            foundProduct.setResolution(request.getResolution());
        }

        if (request.getRefreshRate() != null) {
            foundProduct.setRefreshRate(request.getRefreshRate());
        }

        if (request.getColorGamut() != null) {
            foundProduct.setColorGamut(request.getColorGamut());
        }

        if (request.getPanelType() != null) {
            foundProduct.setPanelType(request.getPanelType());
        }

        if (request.getTouchScreen() != null) {
            foundProduct.setTouchScreen(request.getTouchScreen());
        }

        if (request.getGraphicsCard() != null) {
            foundProduct.setGraphicsCard(request.getGraphicsCard());
        }

        if (request.getSoundTechnology() != null) {
            foundProduct.setSoundTechnology(request.getSoundTechnology());
        }

        if (request.getWirelessConnectivity() != null) {
            foundProduct.setWirelessConnectivity(request.getWirelessConnectivity());
        }

        if (request.getSdCard() != null) {
            foundProduct.setSdCard(request.getSdCard());
        }

        if (request.getWebcam() != null) {
            foundProduct.setWebcam(request.getWebcam());
        }

        if (request.getCoolingFan() != null) {
            foundProduct.setCoolingFan(request.getCoolingFan());
        }

        if (request.getMiscFeature() != null) {
            foundProduct.setMiscFeature(request.getMiscFeature());
        }

        if (request.getBacklitKeyboard() != null) {
            foundProduct.setBacklitKeyboard(request.getBacklitKeyboard());
        }

        if (request.getDimensionWeight() != null) {
            foundProduct.setDimensionWeight(request.getDimensionWeight());
        }

        if (request.getMaterial() != null) {
            foundProduct.setMaterial(request.getMaterial());
        }

        if (request.getBatteryCapacity() != null) {
            foundProduct.setBatteryCapacity(request.getBatteryCapacity());
        }

        if (request.getChargerCapacity() != null) {
            foundProduct.setChargerCapacity(request.getChargerCapacity());
        }

        if (request.getOs() != null) {
            foundProduct.setOs(request.getOs());
        }

        if (request.getLaunchDate() != null) {
            foundProduct.setLaunchDate(request.getLaunchDate());
        }
        
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(SuccessMessage.Product.UPDATED_ONE)
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        productRepository.deleteById(id);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(SuccessMessage.Product.HARD_DELETED_ONE)
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteList(ListIdsRequestDTO request) {
        productRepository.deleteAllById(request.ids());

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> updateListFromExcel(ImportDataRequestDTO request) {
        if (ExcelUtil.notHasExcelFormat(request.file()))
            throw new MalformedFileException(ErrorMessage.Request.MALFORMED_FILE);

        try {
            List<Product> updatedProducts = ExcelUtil.excelToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(updatedProducts);
            return GlobalResponseDTO
                    .<NoPaginatedMeta, BlankData>builder()
                    .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> hideOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        productRepository.deleteById(id);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(SuccessMessage.Product.HIDED_ONE)
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> hideList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setHidden(true));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getHiddenList(PaginationRequestDTO request) {
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
                .<PaginatedMeta, List<BriefProductResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .pagination(Pagination
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> restoreOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setDeleted(false);
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.RESTORED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> restoreList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setDeleted(false));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> exposeOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setHidden(false);
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.EXPOSED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> exposeList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setHidden(false));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> softDeleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setDeleted(true);
        productRepository.save(foundProduct);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Product.SOFT_DELETED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> softDeleteList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setDeleted(true));

        productRepository.saveAll(foundProducts);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
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
    public GlobalResponseDTO<PaginatedMeta, List<BriefProductResponseDTO>> getDeletedList(PaginationRequestDTO request) {
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
                .<PaginatedMeta, List<BriefProductResponseDTO>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .pagination(Pagination
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
