package shop.haui_megatech.domain.dto.order_detail;

import lombok.Builder;
import shop.haui_megatech.domain.entity.Product;

@Builder
public record OrderDetailResponseDTO(
        int quantity,
        float price,
        Product product
) {
}
