package shop.haui_megatech.domain.mapper;

import org.springframework.stereotype.Component;

import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.transfer.ProductDTO;

@Component
public class ProductMapperImpl implements ProductMapper {

	public ProductDTO toProductDTO(Product p) {
		return p == null 
				? null 
				: ProductDTO
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
