package shop.haui_megatech.domain.dto.feedback;

import lombok.Builder;

@Builder
public record ProductInFeedbackDTO(
        String name,
        String mainImageUrl
) {
}
