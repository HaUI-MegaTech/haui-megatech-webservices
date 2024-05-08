package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface FeedbackService {
    CommonResponseDTO<?> addOne();

    CommonResponseDTO<?> updateOne();

    CommonResponseDTO<?> deleteOne();
}
