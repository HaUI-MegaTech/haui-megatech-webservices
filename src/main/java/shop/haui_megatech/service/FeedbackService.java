package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForProduct;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForUser;
import shop.haui_megatech.domain.dto.feedback.FeedbackRequestDTO;
import shop.haui_megatech.domain.dto.global.*;

import java.util.List;

public interface FeedbackService {
    GlobalResponseDTO<NoPaginatedMeta, BlankData> addOne(Integer productId, FeedbackRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(Integer productId, Integer feedbackId, FeedbackRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> delete(Integer userId, String feedbackIds);

    GlobalResponseDTO<PaginatedMeta, List<BriefFeedbackForUser>> getListByUserId(Integer userId, PaginationRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefFeedbackForProduct>> getListByProductId(Integer productId, PaginationRequestDTO request);
}
