package shop.haui_megatech.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.domain.dto.FeedbackDTO;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.entity.Feedback;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.exception.AppException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.FeedbackRepository;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.service.FeedbackService;
import shop.haui_megatech.utility.AuthenticationUtil;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final ProductRepository  productRepository;

    @Override
    public CommonResponseDTO<?> addOne(FeedbackDTO.AddRequest request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        Optional<Product> foundProduct = productRepository.findById(request.productId());

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Feedback newFeedback = Feedback.builder()
                                       .authorName(request.authorName())
                                       .content(request.content())
                                       .rating(request.rating())
                                       .product(foundProduct.get())
                                       .user(requestedUser)
                                       .whenCreated(new Date(Instant.now().toEpochMilli()))
                                       .build();

        feedbackRepository.save(newFeedback);

        return CommonResponseDTO.builder()
                                .success(true)
                                .message("Them thanh cong.")
                                .build();
    }

    @Override
    public CommonResponseDTO<?> updateOne(Integer userId, Integer feedbackId, FeedbackDTO.UpdateRequest request) {
        User requestedUser = AuthenticationUtil.getRequestedUser();
        if (requestedUser == null)
            throw new AppException(ErrorMessage.Auth.UNAUTHORIZED);

        Optional<Feedback> foundFeedback = feedbackRepository.findById(feedbackId);

        if (foundFeedback.isEmpty())
            throw new AppException("Not found");

        if (request.authorName() != null && !request.authorName().isEmpty()) {
            foundFeedback.get().setAuthorName(request.authorName());
        }
        if (request.content() != null && !request.content().isEmpty()) {
            foundFeedback.get().setContent(request.content());
        }
        if (request.rating() != null) {
            foundFeedback.get().setRating(request.rating());
        }

        feedbackRepository.save(foundFeedback.get());

        return CommonResponseDTO.builder()
                                .success(true)
                                .message("Cap nhat thanh cong")
                                .build();
    }

    @Override
    public CommonResponseDTO<?> delete(Integer userId, String feedbackIds) {
        User requestedUser = AuthenticationUtil.getRequestedUser();

        if (requestedUser == null)
            throw new AppException("Khong tim thay");

        return null;
    }

    @Override
    public PaginationDTO.Response<FeedbackDTO.Response> getList(PaginationDTO.Request request) {
        return null;
    }
}
