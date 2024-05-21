package shop.haui_megatech.domain.dto.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record NoPaginationResponseDTO<T>(
        Boolean success,
        String message,
        List<T> items
) {
}
