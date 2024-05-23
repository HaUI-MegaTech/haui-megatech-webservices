package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.log.ActivityLogResponseDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

import java.util.List;

public interface ActivityLogService {
    GlobalResponseDTO<List<ActivityLogResponseDTO>> getList(PaginationRequestDTO request);
}
