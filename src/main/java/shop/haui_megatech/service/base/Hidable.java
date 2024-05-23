package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;

public interface Hidable<T> extends Exposable {
    GlobalResponseDTO<?> hideOne(Integer id);

    GlobalResponseDTO<?> hideList(ListIdsRequestDTO request);

    GlobalResponseDTO<T> getHiddenList(PaginationRequestDTO request);
}
