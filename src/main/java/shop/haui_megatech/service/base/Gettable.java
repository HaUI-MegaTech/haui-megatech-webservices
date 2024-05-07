package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface Gettable<T> {
    CommonResponseDTO<?> getOne(Integer id);

    PaginationDTO.Response<T> getList(PaginationDTO.Request request);
}
