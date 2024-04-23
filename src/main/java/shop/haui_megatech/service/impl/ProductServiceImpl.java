package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ImportDataRequest;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.AddProductRequest;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.ProductDetailDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.mapper.ProductMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.service.ProductService;
import shop.haui_megatech.utility.CsvUtil;
import shop.haui_megatech.utility.ExcelUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MessageSourceUtil messageSourceUtil;

    @Override
    public CommonResponseDTO<ProductDetailDTO> getOne(Integer id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        return CommonResponseDTO.<ProductDetailDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.FOUND))
                                .item(ProductMapper.INSTANCE.toProductDetailDTO(foundProduct.get()))
                                .build();

    }

    @Override
    public PaginationResponseDTO<ProductDTO> getActiveList(PaginationRequestDTO request) {
        if (request.pageIndex() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.order().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.orderBy())
                          .ascending()
                    : Sort.by(request.orderBy())
                          .descending();

        Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);

        Page<Product> page = request.keyword() == null
                             ? productRepository.getActiveProductsPage(pageable)
                             : productRepository.searchActiveProductsPage(request.keyword(), pageable);

        List<Product> products = page.getContent();

        return PaginationResponseDTO.<ProductDTO>builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.pageIndex())
                                    .pageSize(page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(products.parallelStream()
                                                   .map(ProductMapper.INSTANCE::toProductDTO)
                                                   .collect(Collectors.toList()))
                                    .build();
    }

    @Override
    public CommonResponseDTO<ProductDTO> addOne(AddProductRequest request) {

        return CommonResponseDTO.<ProductDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.ADDED_ONE))
                                .item(ProductMapper.INSTANCE.toProductDTO(
                                              productRepository.save(ProductMapper.INSTANCE.toProduct(request))
                                      )
                                )
                                .build();
    }

    @Override
    public CommonResponseDTO<?> importExcel(ImportDataRequest request) {
        try {
            List<Product> products = ExcelUtil.excelToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(products);
            return CommonResponseDTO.builder()
                                    .success(true)
                                    .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.IMPORTED_LIST,
                                                                          savedProducts.size()))
                                    .build();
        } catch (IOException e) {
            throw new RuntimeException("Excel data is failed to store: " + e.getMessage());
        }
    }

    @Override
    public CommonResponseDTO<?> importCsv(ImportDataRequest request) {
        try {
            List<Product> products = CsvUtil.csvToProducts(request.file().getInputStream());
            List<Product> savedProducts = productRepository.saveAll(products);
            return CommonResponseDTO.builder()
                                    .success(true)
                                    .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.IMPORTED_LIST,
                                                                          savedProducts.size()))
                                    .build();
        } catch (IOException ex) {
            throw new RuntimeException("Data is not store successfully: " + ex.getMessage());
        }
    }

    @Override
    public CommonResponseDTO<?> updateOne(
            Integer id,
            UpdateProductRequest request
    ) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        Product foundProduct = found.get();

        if (request.name() != null) foundProduct.setName(request.name());
        if (request.price() != null) foundProduct.setCurrentPrice(request.price());
        productRepository.save(foundProduct);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(SuccessMessageConstant.Product.UPDATED)
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        productRepository.deleteById(id);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(SuccessMessageConstant.Product.HARD_DELETED_ONE)
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        productRepository.deleteAllById(request.ids());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.HARD_DELETED_LIST,
                                                                      request.ids().size()))
                                .build();
    }


    @Override
    public PaginationResponseDTO<ProductDTO> getActiveListByBrand(
            PaginationRequestDTO request,
            Integer brandId
    ) {
        if (request.pageIndex() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.order().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.orderBy())
                          .ascending()
                    : Sort.by(request.orderBy())
                          .descending();

        Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);

        Page<Product> page = productRepository.getActiveListByBrand(brandId, pageable);

        List<Product> products = page.getContent();

        return PaginationResponseDTO.<ProductDTO>builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.pageIndex())
                                    .pageSize(page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(products.parallelStream()
                                                   .map(ProductMapper.INSTANCE::toProductDTO)
                                                   .collect(Collectors.toList()))
                                    .build();
    }

    @Override
    public CommonResponseDTO<?> hideOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        productRepository.deleteById(id);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(SuccessMessageConstant.Product.HIDED_ONE)
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hideList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setHidden(true));

        productRepository.saveAll(foundProducts);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.HIDED_LIST,
                                                                      foundProducts.size()
                                         )
                                )
                                .build();
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getHiddenList(PaginationRequestDTO request) {
        if (request.pageIndex() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.order().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.orderBy())
                          .ascending()
                    : Sort.by(request.orderBy())
                          .descending();

        Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);

        Page<Product> page = request.keyword() == null
                             ? productRepository.getHiddenProductsPage(pageable)
                             : productRepository.searchHiddenProductsPage(request.keyword(), pageable);

        List<Product> products = page.getContent();

        return PaginationResponseDTO.<ProductDTO>builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.pageIndex())
                                    .pageSize(page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(products.parallelStream()
                                                   .map(ProductMapper.INSTANCE::toProductDTO)
                                                   .collect(Collectors.toList()))
                                    .build();
    }

    @Override
    public CommonResponseDTO<?> restoreOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setDeleted(false);
        productRepository.save(foundProduct);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.RESTORED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> restoreList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setDeleted(false));

        productRepository.saveAll(foundProducts);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.RESTORED_LIST,
                                                                      foundProducts.size()
                                         )
                                )
                                .build();
    }

    @Override
    public CommonResponseDTO<?> exposeOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setHidden(false);
        productRepository.save(foundProduct);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.UNHIDED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> exposeList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setHidden(false));

        productRepository.saveAll(foundProducts);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.UNHIDED_LIST,
                                                                      foundProducts.size()
                                         )
                                )
                                .build();
    }

    @Override
    public CommonResponseDTO<?> softDeleteOne(Integer id) {
        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        Product foundProduct = found.get();
        foundProduct.setDeleted(true);
        productRepository.save(foundProduct);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.SOFT_DELETED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request) {
        List<Product> foundProducts = productRepository.findAllById(request.ids());

        foundProducts.parallelStream().forEach(item -> item.setDeleted(true));

        productRepository.saveAll(foundProducts);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.SOFT_DELETED_LIST,
                                                                      foundProducts.size()
                                         )
                                )
                                .build();
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getDeletedList(PaginationRequestDTO request) {
        if (request.pageIndex() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.order().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.orderBy())
                          .ascending()
                    : Sort.by(request.orderBy())
                          .descending();

        Pageable pageable = PageRequest.of(request.pageIndex(), request.pageSize(), sort);

        Page<Product> page = request.keyword() == null
                             ? productRepository.getDeletedProductsPage(pageable)
                             : productRepository.searchDeletedProductsPage(request.keyword(), pageable);

        List<Product> products = page.getContent();

        return PaginationResponseDTO.<ProductDTO>builder()
                                    .keyword(request.keyword())
                                    .pageIndex(request.pageIndex())
                                    .pageSize(page.getNumberOfElements())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(products.parallelStream()
                                                   .map(ProductMapper.INSTANCE::toProductDTO)
                                                   .collect(Collectors.toList()))
                                    .build();
    }
}
