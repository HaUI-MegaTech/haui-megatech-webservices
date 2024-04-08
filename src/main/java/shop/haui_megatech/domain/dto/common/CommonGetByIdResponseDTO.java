package shop.haui_megatech.domain.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record CommonGetByIdResponseDTO<T>(
        Boolean result,
        String message,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        T data
) {
}
