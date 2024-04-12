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
import shop.haui_megatech.domain.mapper.ProductMapperImpl;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.util.MessageSourceUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MessageSourceUtil messageSourceUtil;
    private final ProductMapper mapper;

    public CommonResponseDTO<ProductDTO> getProductById(Integer productId) {

        Optional<Product> foundProduct = productRepository.findById(productId);

        return foundProduct.isPresent()
                ? CommonResponseDTO.<ProductDTO>builder()
                                   .result(true)
                                   .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.FOUND))
                                   .item(mapper.toProductDTO(foundProduct.get()))
                                   .build()
                : CommonResponseDTO.<ProductDTO>builder()
                                   .result(false)
                                   .message(messageSourceUtil.getMessage(ErrorMessageConstant.Product.NOT_FOUND))
                                   .item(null)
                                   .build();
    }

    @Override
    public CommonResponseDTO<ProductDTO> createProduct(CreateProductRequest request) {
        return CommonResponseDTO.<ProductDTO>builder()
                                .result(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Product.CREATED))
                                .item(mapper.toProductDTO(
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
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(ErrorMessageConstant.Product.NOT_FOUND)
                                    .build();

        Product foundProduct = found.get();
        foundProduct.setName(request.name());
        foundProduct.setNewPrice(request.price());
        productRepository.save(foundProduct);

        return CommonResponseDTO.builder()
                                .result(true)
                                .message(SuccessMessageConstant.Product.UPDATED)
                                .build();
    }

    @Override
    public CommonResponseDTO<?> deleteProductById(Integer id) {

        Optional<Product> found = productRepository.findById(id);

        if (found.isEmpty())
            return CommonResponseDTO.builder()
                                    .result(false)
                                    .message(ErrorMessageConstant.Product.NOT_FOUND)
                                    .build();

        productRepository.deleteById(id);
        return CommonResponseDTO.builder()
                                .result(true)
                                .message(SuccessMessageConstant.Product.DELETED)
                                .build();
    }

    @Override
    public PaginationResponseDTO<ProductDTO> getProducts(PaginationRequestDTO request) {
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
                                    .pageSize(request.pageSize())
                                    .totalItems(page.getTotalElements())
                                    .totalPages(page.getTotalPages())
                                    .items(products.parallelStream()
                                                   .map(mapper::toProductDTO)
                                                   .collect(Collectors.toList()))
                                    .build();
    }

}
