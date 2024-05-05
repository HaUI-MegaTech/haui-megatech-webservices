package shop.haui_megatech.domain.dto.order;

import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;

public record ListOrdersForUserRequestDTO(
        String token,
        PaginationRequestDTO paginationRequestDTO
) {
}
