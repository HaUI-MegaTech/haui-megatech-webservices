package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface Addable<T> {
    CommonResponseDTO<?> addOne(T request);
}
