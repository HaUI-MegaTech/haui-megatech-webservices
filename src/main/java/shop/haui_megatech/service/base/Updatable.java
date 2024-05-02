package shop.haui_megatech.service.base;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface Updatable<T> {
    CommonResponseDTO<?> updateOne(Integer id, T request);
}
