package shop.haui_megatech.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForProduct;
import shop.haui_megatech.domain.dto.feedback.BriefFeedbackForUser;
import shop.haui_megatech.domain.dto.feedback.FeedbackRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.entity.Feedback;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.FeedbackMapper;
import shop.haui_megatech.exception.AppException;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.FeedbackRepository;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.service.FeedbackService;
import shop.haui_megatech.utility.AuthenticationUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProductRepository  productRepository;
    private final MessageSourceUtil  messageSourceUtil;

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> addOne(Integer productId, FeedbackRequestDTO request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        Optional<Product> foundProduct = productRepository.findById(productId);

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        float currentAverageRating = foundProduct.get().getAverageRating();
        int currentFeedbacks = foundProduct.get().getFeedbacks().size();
        float newAverageRating = (currentAverageRating * currentFeedbacks + request.rating()) / (currentFeedbacks + 1);
        foundProduct.get().setAverageRating(newAverageRating);
        foundProduct.get().setFeedbacksCount(currentFeedbacks + 1);
        productRepository.save(foundProduct.get());

        Feedback newFeedback = Feedback.builder()
                                       .alias(request.alias())
                                       .content(request.content())
                                       .rating(request.rating())
                                       .product(foundProduct.get())
                                       .user(requestedUser)
                                       .whenCreated(new Date(Instant.now().toEpochMilli()))
                                       .build();

        feedbackRepository.save(newFeedback);


        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Feedback.ADDED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(Integer productId, Integer feedbackId, FeedbackRequestDTO request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        Optional<Feedback> foundFeedback = feedbackRepository.findById(feedbackId);

        if (foundFeedback.isEmpty())
            throw new AppException(ErrorMessage.Feedback.NOT_FOUND);

        foundFeedback.ifPresent(item -> {
            if (item.getLastUpdated() != null)
                throw new AppException(ErrorMessage.Feedback.EXCEED_UPDATE_COUNT);
        });

        if (request.alias() != null && !request.alias().isEmpty()) {
            foundFeedback.get().setAlias(request.alias());
        }
        if (request.content() != null && !request.content().isEmpty()) {
            foundFeedback.get().setContent(request.content());
        }
        if (request.rating() != null) {
            foundFeedback.get().setRating(request.rating());
        }

        feedbackRepository.save(foundFeedback.get());

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Feedback.UPDATED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, BlankData> delete(Integer userId, String feedbackIds) {
        User requestedUser = AuthenticationUtil.getRequestedUser();

        if (requestedUser == null)
            throw new NotFoundException(ErrorMessage.Feedback.NOT_FOUND);

        return GlobalResponseDTO
                .<NoPaginatedMeta, BlankData>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .message(messageSourceUtil.getMessage(SuccessMessage.Feedback.UPDATED_ONE))
                        .build()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<BriefFeedbackForUser>> getListByUserId(
            Integer userId,
            PaginationRequestDTO request
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<Feedback> page = feedbackRepository.findAllByUserId(userId, pageable);

        List<Feedback> feedbacks = page.getContent();

        return GlobalResponseDTO
                .<PaginatedMeta, List<BriefFeedbackForUser>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .pagination(Pagination
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize(request.limit())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build()
                )
                .data(feedbacks
                        .parallelStream()
                        .map(FeedbackMapper.INSTANCE::toBriefFeedbackForUser)
                        .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<PaginatedMeta, List<BriefFeedbackForProduct>> getListByProductId(
            Integer productId,
            PaginationRequestDTO request
    ) {
        if (request.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        Page<Feedback> page = feedbackRepository.findALlByProductId(productId, pageable);

        List<Feedback> feedbacks = page.getContent();

        return GlobalResponseDTO
                .<PaginatedMeta, List<BriefFeedbackForProduct>>builder()
                .meta(PaginatedMeta
                        .builder()
                        .pagination(Pagination
                                .builder()
                                .keyword(request.keyword())
                                .pageIndex(request.index())
                                .pageSize(request.limit())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build())
                .data(feedbacks
                        .parallelStream()
                        .map(FeedbackMapper.INSTANCE::toBriefFeedbackForProduct)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
