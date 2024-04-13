package shop.haui_megatech.domain.mapper;

import org.springframework.stereotype.Component;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.User;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDTO dto) {
        return User.builder()
                   .username(dto.username())
                   .firstName(dto.firstName())
                   .lastName(dto.lastName())
                   .avatar(dto.avatar())
                   .email(dto.email())
                   .phoneNumber(dto.phoneNumber())
                   .build();
    }

    @Override
    public UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                      .id(user.getId())
                      .username(user.getUsername())
                      .firstName(user.getFirstName())
                      .lastName(user.getLastName())
                      .avatar(user.getAvatar())
                      .email(user.getEmail())
                      .phoneNumber(user.getPhoneNumber())
                      .build();
    }
}
