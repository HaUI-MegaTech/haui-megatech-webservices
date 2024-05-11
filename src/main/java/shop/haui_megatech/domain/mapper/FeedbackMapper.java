package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.FeedbackDTO;
import shop.haui_megatech.domain.entity.Feedback;

@Mapper
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    FeedbackDTO.UserResponse toUserFeedbackDTO(Feedback feedback);

    FeedbackDTO.ProductResponse toProductFeedbackDTO(Feedback feedback);
}
