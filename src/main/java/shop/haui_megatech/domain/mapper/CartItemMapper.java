package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.CartItemDTO;
import shop.haui_megatech.domain.entity.CartItem;

@Mapper
public interface CartItemMapper {
    CartItemMapper INSTANCE = Mappers.getMapper(CartItemMapper.class);

    @Mapping(target = "id", source = "id")
    CartItemDTO.Response toCartItemResponseDTO(CartItem cartItem);
}
