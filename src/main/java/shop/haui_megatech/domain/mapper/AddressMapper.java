package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.AddressDTO;
import shop.haui_megatech.domain.entity.Address;

@Mapper
public interface AddressMapper {
    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address toAddress(AddressDTO.Request request);
}
