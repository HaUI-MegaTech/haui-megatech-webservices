package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;

public interface Hidable<T> extends Exposable {
    CommonResponseDTO<?> hideOne(Integer id);

    CommonResponseDTO<?> hideList(ListIdsRequestDTO request);

    PaginationDTO.Response<T> getHiddenList(PaginationDTO.Request request);
}
