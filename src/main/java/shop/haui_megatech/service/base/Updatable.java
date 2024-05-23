package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface Updatable<T> {
    GlobalResponseDTO<?> updateOne(Integer id, T request);
}
