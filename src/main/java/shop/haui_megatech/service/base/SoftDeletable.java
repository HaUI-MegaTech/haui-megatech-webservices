package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface SoftDeletable<T> {
    CommonResponseDTO<?> softDeleteOne(Integer id);

    CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request);

    PaginationResponseDTO<T> getDeletedList(PaginationRequestDTO request);
}
