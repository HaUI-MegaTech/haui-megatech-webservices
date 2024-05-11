package shop.haui_megatech.controller;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.FeedbackDTO;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.service.FeedbackService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
public class FeedbackRestController {
    private final FeedbackService feedbackService;

    @PostMapping(Endpoint.Feedback.ADD_ONE)
    public ResponseEntity<?> addOne(
            @PathVariable Integer userId,
            @RequestBody FeedbackDTO.AddRequest feedback
    ) {
        return ResponseUtil.created(feedbackService.addOne(feedback));
    }

    @PutMapping(Endpoint.Feedback.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable Integer userId,
            @PathVariable Integer feedbackId,
            @RequestBody FeedbackDTO.UpdateRequest feedback
    ) {
        return ResponseUtil.ok(feedbackService.updateOne(userId, feedbackId, feedback));
    }

    @GetMapping(Endpoint.Feedback.GET_LIST_BY_USER)
    public ResponseEntity<?> getListByUser(
            @PathVariable Integer userId,
            @ParameterObject PaginationDTO.Request request
    ) {
        return ResponseUtil.ok(feedbackService.getListByUserId(userId, request));
    }

    @GetMapping(Endpoint.Feedback.GET_LIST_BY_PRODUCT)
    public ResponseEntity<?> getListByProduct(
            @PathVariable Integer productId,
            @ParameterObject PaginationDTO.Request request
    ) {
        return ResponseUtil.ok(feedbackService.getListByProductId(productId, request));
    }


}
