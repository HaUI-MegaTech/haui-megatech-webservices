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
import shop.haui_megatech.domain.dto.product.CreateProductRequest;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.mapper.ProductMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.service.ProductService;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MessageSourceUtil messageSourceUtil;

    public CommonResponseDTO<ProductDTO> getOne(Integer id) {
        Optional<Product> foundProduct = productRepository.findById(id);

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        return CommonResponseDTO.<ProductDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.FOUND))
                                .item(ProductMapper.INSTANCE.toProductDTO(foundProduct.get()))
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
                ? productRepository.findAll(pageable)
                : productRepository.searchProducts(request.keyword(), pageable);

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
    public CommonResponseDTO<ProductDTO> addOne(CreateProductRequest request) {

        return CommonResponseDTO.<ProductDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.CREATED))
                                .item(ProductMapper.INSTANCE.toProductDTO(
                                        productRepository.save(Product.builder()
                                                                      .name(request.name())
                                                                      .oldPrice(request.price())
                                                                      .build()
                                        )))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> importExcel(ImportDataRequest request) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> importCsv(ImportDataRequest request) {
        return null;
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
                                .message(SuccessMessageConstant.Product.DELETED)
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        return null;
    }


    @Override
    public PaginationResponseDTO<ProductDTO> getActiveListByBrand(
            PaginationRequestDTO request,
            Integer brandId
    ) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> hideOne(Integer id) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> hideList(ListIdsRequestDTO request) {
        return null;
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getHiddenList(PaginationRequestDTO request) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> restoreOne(Integer id) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> restoreList(ListIdsRequestDTO request) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> showOne(Integer productId) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> showList(ListIdsRequestDTO request) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> softDeleteOne(Integer id) {
        return null;
    }

    @Override
    public CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request) {
        return null;
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getDeletedList(PaginationRequestDTO request) {
        return null;
    }
}
