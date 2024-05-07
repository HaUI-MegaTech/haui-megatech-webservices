package shop.haui_megatech.domain.dto.order;

import shop.haui_megatech.domain.dto.PaginationDTO;

public record ListOrdersForUserRequestDTO(
        String token,
        PaginationDTO.Request paginationRequestDTO
) {
}
