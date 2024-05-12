package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForProduct;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForUser;
import shop.haui_megatech.domain.dto.feedback.FeedbackRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface FeedbackService {
    CommonResponseDTO<?> addOne(Integer productId, FeedbackRequestDTO request);

    CommonResponseDTO<?> updateOne(Integer productId, Integer feedbackId, FeedbackRequestDTO request);

    CommonResponseDTO<?> delete(Integer userId, String feedbackIds);

    PaginationResponseDTO<BriefFeedbackForUser> getListByUserId(Integer userId, PaginationRequestDTO request);

    PaginationResponseDTO<BriefFeedbackForProduct> getListByProductId(Integer productId, PaginationRequestDTO request);
}
