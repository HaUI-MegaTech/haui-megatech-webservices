package shop.haui_megatech.domain.dto;

import lombok.Builder;

public record PaymentDTO() {

    @Builder
    public record Response(
            Boolean success,
            String message,
            String url
    ) {}
}
