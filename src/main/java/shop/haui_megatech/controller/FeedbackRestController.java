package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.feedback.FeedbackRequestDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.service.FeedbackService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Feedbacks Management REST API")
public class FeedbackRestController {
    private final FeedbackService feedbackService;

    @Operation(
            summary = "API Add Feedback",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @PostMapping(Endpoint.V1.Feedback.ADD_ONE)
    public ResponseEntity<?> addOne(
            @PathVariable Integer productId,
            @RequestBody FeedbackRequestDTO feedback
    ) {
        return ResponseUtil.created(feedbackService.addOne(productId, feedback));
    }


    @Operation(
            summary = "API Update Feedback",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @PutMapping(Endpoint.V1.Feedback.UPDATE_ONE)
    public ResponseEntity<?> updateOne(
            @PathVariable Integer productId,
            @PathVariable Integer feedbackId,
            @RequestBody FeedbackRequestDTO feedback
    ) {
        return ResponseUtil.ok(feedbackService.updateOne(productId, feedbackId, feedback));
    }


    @Operation(
            summary = "API Get Feedbacks by User Id",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "403", description = "When has not been authorized"),
            }
    )
    @GetMapping(Endpoint.V1.Feedback.GET_LIST_BY_USER)
    public ResponseEntity<?> getListByUser(
            @PathVariable Integer userId,
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(feedbackService.getListByUserId(userId, request));
    }


    @Operation(summary = "API Get Feedback by Product Id")
    @GetMapping(Endpoint.V1.Feedback.GET_LIST_BY_PRODUCT)
    public ResponseEntity<?> getListByProduct(
            @PathVariable Integer productId,
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseUtil.ok(feedbackService.getListByProductId(productId, request));
    }


}
