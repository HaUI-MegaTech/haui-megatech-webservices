package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface Exposable {
    GlobalResponseDTO<?> exposeOne(Integer id);

    GlobalResponseDTO<?> exposeList(ListIdsRequestDTO request);
}
