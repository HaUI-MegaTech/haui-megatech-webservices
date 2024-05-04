package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface HardDeletable {
    CommonResponseDTO<?> hardDeleteOne(Integer id);

    CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request);
}
