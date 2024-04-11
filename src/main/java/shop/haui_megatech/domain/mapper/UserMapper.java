package shop.haui_megatech.domain.mapper;

import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.User;

public class UserMapper {
    public static User toUser(UserDTO dto) {
        return User.builder()
                   .username(dto.username())
                   .firstName(dto.firstName())
                   .lastName(dto.lastName())
                   .avatar(dto.avatar())
                   .email(dto.email())
                   .build();
    }

    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                      .id(user.getId())
                      .username(user.getUsername())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .avatar(user.getAvatar())
                      .email(user.getEmail())
                      .build();
    }
}
