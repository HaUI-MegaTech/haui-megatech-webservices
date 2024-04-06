package shop.haui_megatech.domain.mapper;

import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.transfer.ProductDTO;

public interface ProductMapper {
	
	ProductDTO toProductDTO(Product p);
	Product toProduct(ProductDTO dto);
}
