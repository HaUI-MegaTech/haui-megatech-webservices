package shop.haui_megatech.domain.dto;

import lombok.Builder;

@Builder
public record PaymentResponseDTO(
        Boolean success,
        String message,
        String url
) {
}
