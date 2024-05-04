package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.product.AddProductRequestDTO;
import shop.haui_megatech.domain.dto.product.ProductDTO;
import shop.haui_megatech.domain.dto.product.ProductDetailDTO;
import shop.haui_megatech.domain.entity.Product;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mappings(
            value = {
                    @Mapping(target = "weight", source = "shortWeight"),
                    @Mapping(target = "ram", source = "memoryCapacity"),
                    @Mapping(target = "newPrice", source = "currentPrice"),
                    @Mapping(target = "display", source = "shortDisplay"),
                    @Mapping(target = "card", source = "shortCard"),
                    @Mapping(target = "battery", source = "batteryCapacity"),
            }
    )
    ProductDTO toProductDTO(Product product);

    ProductDetailDTO toProductDetailDTO(Product product);

    Product toProduct(AddProductRequestDTO request);
}
