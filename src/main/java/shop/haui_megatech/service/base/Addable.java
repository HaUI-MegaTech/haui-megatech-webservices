package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface Addable<T> {
    GlobalResponseDTO<?> addOne(T request);
}
