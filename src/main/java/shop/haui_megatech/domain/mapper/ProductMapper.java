package shop.haui_megatech.domain.mapper;

import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.entity.Product;

public interface ProductMapper {
    ProductDTO toProductDTO(Product product);
    Product toProduct(ProductDTO productDTO);
}
