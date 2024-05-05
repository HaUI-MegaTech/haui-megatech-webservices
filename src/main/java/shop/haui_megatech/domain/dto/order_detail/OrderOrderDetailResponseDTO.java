package shop.haui_megatech.domain.dto.order_detail;

import lombok.Builder;

@Builder
public record OrderOrderDetailResponseDTO(
        int quatity,
        String price,
        String proName
) {
}
