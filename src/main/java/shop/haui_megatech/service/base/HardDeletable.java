package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface HardDeletable<T> {
    CommonResponseDTO<?> hardDeleteOne(T request);

    CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request);
}
