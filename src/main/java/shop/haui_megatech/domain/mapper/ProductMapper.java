package shop.haui_megatech.domain.mapper;

import jakarta.annotation.Nullable;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.dto.ProductDTO;

import java.util.Optional;

public interface ProductMapper {
	
	ProductDTO toProductDTO(Product p);
	Product toProduct(ProductDTO dto);
}
