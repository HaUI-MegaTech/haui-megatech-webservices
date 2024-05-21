package shop.haui_megatech.domain.dto.payment;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentResponseDTO(
        Boolean success,
        String message,
        String url
) {
}
