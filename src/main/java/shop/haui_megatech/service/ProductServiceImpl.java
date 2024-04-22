package shop.haui_megatech.service;

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
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MessageSourceUtil messageSourceUtil;

    public CommonResponseDTO<ProductDTO> getProductById(Integer productId) {
        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        return CommonResponseDTO.<ProductDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.FOUND))
                                .item(ProductMapper.INSTANCE.toProductDTO(foundProduct.get()))
                                .build();

    }

    @Override
    public CommonResponseDTO<ProductDTO> createProduct(CreateProductRequest request) {

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
    public CommonResponseDTO<?> updateProduct(
            Integer productId,
            UpdateProductRequest request
    ) {
        Optional<Product> found = productRepository.findById(productId);

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
    public CommonResponseDTO<?> deleteProductById(Integer id) {

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
    public PaginationResponseDTO<ProductDTO> getProducts(PaginationRequestDTO request) {
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

}
