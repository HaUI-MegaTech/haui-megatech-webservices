package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface Unhidable {
    CommonResponseDTO<?> unhideOne(Integer productId);

    CommonResponseDTO<?> unhideList(ListIdsRequestDTO request);
}
