package shop.haui_megatech.domain.mapper;

import org.springframework.stereotype.Component;

import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.dto.ProductDTO;

import java.util.Optional;

@Component
public class ProductMapperImpl implements ProductMapper {

	public ProductDTO toProductDTO(Product p) {
		return ProductDTO
				.builder()
				.name(p.getName())
				.price(p.getPrice())
				.build();
	}

	@Override
	public Product toProduct(ProductDTO dto) {
		return Product
				.builder()
				.name(dto.name())
				.price(dto.price())
				.build();
	}

}
