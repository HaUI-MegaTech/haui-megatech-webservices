package shop.haui_megatech.domain.dto.pagination;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
public record PaginationResponseDTO<T>(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String keyword,
        Integer pageIndex,
        Integer pageSize,
        Long totalItems,
        Integer totalPages,
        List<T> items
) {

}
