package shop.haui_megatech.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForProduct;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForUser;
import shop.haui_megatech.domain.entity.Feedback;

@Mapper
public interface FeedbackMapper {
    FeedbackMapper INSTANCE = Mappers.getMapper(FeedbackMapper.class);

    BriefFeedbackForUser toBriefFeedbackForUser(Feedback feedback);

    BriefFeedbackForProduct toBriefFeedbackForProduct(Feedback feedback);
}
