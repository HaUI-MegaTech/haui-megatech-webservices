package shop.haui_megatech.domain.dto.pagination;

import lombok.Builder;

import java.util.List;

@Builder
public record NoPaginationResponseDTO<T>(
        String message,
        List<T> items
) {
}
