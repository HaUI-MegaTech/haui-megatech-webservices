package shop.haui_megatech.domain.transfer.pagination;

import java.util.List;

import lombok.Builder;

@Builder
public record PaginationResponseDTO<T>(
		Integer pageIndex,
		Short pageSize,
		Long totalItems,
		Integer totalPages,
		List<T> data
) {
	
}
