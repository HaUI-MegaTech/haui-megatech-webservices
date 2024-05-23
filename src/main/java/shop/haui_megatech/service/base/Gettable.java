package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface Gettable<T> {
    GlobalResponseDTO<?> getOne(Integer id);

    GlobalResponseDTO<T> getList(PaginationRequestDTO request);
}
