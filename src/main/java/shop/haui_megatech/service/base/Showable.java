package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface Showable {
    CommonResponseDTO<?> showOne(Integer productId);

    CommonResponseDTO<?> showList(ListIdsRequestDTO request);
}
