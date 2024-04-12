package shop.haui_megatech.domain.mapper;

import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.User;

public interface UserMapper {
    UserDTO toUserDTO(User user);
    User toUser(UserDTO userDTO);
}
