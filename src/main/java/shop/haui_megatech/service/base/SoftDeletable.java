package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface SoftDeletable<T> extends Restorable {
    CommonResponseDTO<?> softDeleteOne(Integer id);

    CommonResponseDTO<?> softDeleteList(ListIdsRequestDTO request);

    PaginationDTO.Response<T> getDeletedList(PaginationDTO.Request request);
}
