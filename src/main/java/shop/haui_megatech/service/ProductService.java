package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.ProductDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface ProductService {
    CommonResponseDTO<ProductDTO> getProductById(Integer productId);

    CommonResponseDTO<ProductDTO> createProduct(ProductDTO dto);

    CommonResponseDTO<?> updateProduct(Integer productId, ProductDTO dto);

    CommonResponseDTO<?> deleteProductById(Integer id);

    PaginationResponseDTO<ProductDTO> getProducts(PaginationRequestDTO request);
}
