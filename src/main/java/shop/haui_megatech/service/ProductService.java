package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.CreateProductRequest;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.UpdateProductRequest;

public interface ProductService {
    CommonResponseDTO<ProductDTO> getProductById(Integer productId);

    CommonResponseDTO<ProductDTO> createProduct(CreateProductRequest request);

    CommonResponseDTO<?> updateProduct(Integer productId, UpdateProductRequest request);

    CommonResponseDTO<?> deleteProductById(Integer id);

    PaginationResponseDTO<ProductDTO> getProducts(PaginationRequestDTO request);
}
