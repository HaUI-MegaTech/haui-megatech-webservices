package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.FeedbackDTO;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;

public interface FeedbackService {
    CommonResponseDTO<?> addOne(FeedbackDTO.AddRequest request);

    CommonResponseDTO<?> updateOne(Integer userId, Integer feedbackId, FeedbackDTO.UpdateRequest request);

    CommonResponseDTO<?> delete(Integer userId, String feedbackIds);

    PaginationDTO.Response<FeedbackDTO.UserResponse> getListByUserId(Integer userId, PaginationDTO.Request request);

    PaginationDTO.Response<FeedbackDTO.ProductResponse> getListByProductId(Integer productId, PaginationDTO.Request request);
}
