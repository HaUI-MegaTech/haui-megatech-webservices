package shop.haui_megatech.domain.dto.pagination;

import java.util.List;

import lombok.Builder;

@Builder
public record PaginationResponseDTO<T>(
		Integer pageIndex,
		Short pageSize,
		Long totalItems,
		Integer totalPages,
		List<T> items
) {
	
}
