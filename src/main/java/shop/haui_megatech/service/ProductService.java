package shop.haui_megatech.service;

import shop.haui_megatech.domain.transfer.ProductDTO;
import shop.haui_megatech.domain.transfer.common.CommonResponseDTO;
import shop.haui_megatech.domain.transfer.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.transfer.pagination.PaginationResponseDTO;

public interface ProductService {
	public ProductDTO getProductById(Integer productId);
	public ProductDTO createProduct(ProductDTO dto);
	public CommonResponseDTO updateProduct(Integer productId, ProductDTO dto);
	public CommonResponseDTO deleteProductById(Integer id);
	public PaginationResponseDTO<ProductDTO> getProducts(PaginationRequestDTO request);
}
