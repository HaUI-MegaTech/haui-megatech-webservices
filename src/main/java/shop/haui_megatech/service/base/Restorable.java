package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface Restorable {
    CommonResponseDTO<?> restoreOne(Integer id);

    CommonResponseDTO<?> restoreList(ListIdsRequestDTO request);
}
