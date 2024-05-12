package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.user.BriefUserResponseDTO;
import shop.haui_megatech.domain.dto.user.FullUserResponseDTO;
import shop.haui_megatech.domain.entity.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    BriefUserResponseDTO toBriefUserResponseDTO(User user);

    FullUserResponseDTO toUserFullResponseDTO(User user);
}
