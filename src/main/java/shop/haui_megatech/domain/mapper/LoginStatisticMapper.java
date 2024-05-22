package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.home.LoginStatisticResponseDTO;
import shop.haui_megatech.domain.entity.LoginStatistic;

@Mapper
public interface LoginStatisticMapper {
    LoginStatisticMapper INSTANCE = Mappers.getMapper(LoginStatisticMapper.class);

    LoginStatisticResponseDTO toLoginStatisticResponseDTO(LoginStatistic loginStatistic);
}
