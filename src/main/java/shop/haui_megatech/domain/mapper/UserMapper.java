package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.dto.user.UserDetailDTO;
import shop.haui_megatech.domain.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toUserDTO(User user);

    UserDetailDTO toUserDetailDTO(User user);

    User toUser(UserDTO userDTO);
}
