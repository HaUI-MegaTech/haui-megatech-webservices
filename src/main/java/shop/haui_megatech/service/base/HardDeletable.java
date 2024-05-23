package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface HardDeletable {
    GlobalResponseDTO<?> hardDeleteOne(Integer id);

    GlobalResponseDTO<?> hardDeleteList(ListIdsRequestDTO request);
}
