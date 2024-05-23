package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForProduct;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForUser;
import shop.haui_megatech.domain.dto.feedback.FeedbackRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;

import java.util.List;

public interface FeedbackService {
    GlobalResponseDTO<?> addOne(Integer productId, FeedbackRequestDTO request);

    GlobalResponseDTO<?> updateOne(Integer productId, Integer feedbackId, FeedbackRequestDTO request);

    GlobalResponseDTO<?> delete(Integer userId, String feedbackIds);

    GlobalResponseDTO<List<BriefFeedbackForUser>> getListByUserId(Integer userId, PaginationRequestDTO request);

    GlobalResponseDTO<List<BriefFeedbackForProduct>> getListByProductId(Integer productId, PaginationRequestDTO request);
}
