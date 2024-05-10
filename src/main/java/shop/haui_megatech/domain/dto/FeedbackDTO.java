package shop.haui_megatech.domain.dto;

import java.util.Date;

public record FeedbackDTO() {
    public record AddRequest(
            String authorName,
            String content,
            Byte rating,
            Integer productId
    ) {}

    public record UpdateRequest(
            String authorName,
            String content,
            Byte rating
    ) {}

    public record Response(
            Integer id,
            String authorName,
            String content,
            Byte rating,
            Date whenCreated,
            ProductDTO.MinimizedResponse product
    ) {}
}
