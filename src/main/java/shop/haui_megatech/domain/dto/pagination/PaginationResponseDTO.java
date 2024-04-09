package shop.haui_megatech.domain.dto.pagination;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
public record PaginationResponseDTO<T>(
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String keyword,
		Integer pageIndex,
		Short pageSize,
		Long totalItems,
		Integer totalPages,
		List<T> items
) {
	
}
