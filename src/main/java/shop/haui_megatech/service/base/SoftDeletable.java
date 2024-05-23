package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;

public interface SoftDeletable<T> extends Restorable {
    GlobalResponseDTO<?> softDeleteOne(Integer id);

    GlobalResponseDTO<?> softDeleteList(ListIdsRequestDTO request);

    GlobalResponseDTO<T> getDeletedList(PaginationRequestDTO request);
}
