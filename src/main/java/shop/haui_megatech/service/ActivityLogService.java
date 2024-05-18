package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.log.ActivityLogResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface ActivityLogService {
    PaginationResponseDTO<ActivityLogResponseDTO> getList(PaginationRequestDTO request);
}
