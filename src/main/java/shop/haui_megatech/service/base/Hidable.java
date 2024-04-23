package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface Hidable<T> {
    CommonResponseDTO<?> hideOne(Integer id);

    CommonResponseDTO<?> hideList(ListIdsRequestDTO request);

    PaginationResponseDTO<T> getHiddenList(PaginationRequestDTO request);
}