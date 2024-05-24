package shop.haui_megatech.domain.dto.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaginatedMeta(
        Status status,
        String message,
        Pagination pagination
) {
}
