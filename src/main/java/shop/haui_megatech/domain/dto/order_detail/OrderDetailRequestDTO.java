package shop.haui_megatech.domain.dto.order_detail;

import lombok.Builder;

@Builder
public record OrderDetailRequestDTO(
        int quantity,
        int productId
) {
}
