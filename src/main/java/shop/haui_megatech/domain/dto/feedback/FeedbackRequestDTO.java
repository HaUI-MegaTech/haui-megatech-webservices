package shop.haui_megatech.domain.dto.feedback;

public record FeedbackRequestDTO(
        String alias,
        String content,
        Byte rating
) {
}
