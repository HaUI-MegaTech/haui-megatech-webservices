package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface Gettable<T> {
    CommonResponseDTO<?> getOne(Integer id);

    PaginationResponseDTO<T> getList(PaginationRequestDTO request);
}
