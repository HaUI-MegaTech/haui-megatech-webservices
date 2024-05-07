package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.UserDTO;
import shop.haui_megatech.domain.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO.Response toUserDTO(User user);

    UserDTO.DetailResponse toUserDetailDTO(User user);
}
