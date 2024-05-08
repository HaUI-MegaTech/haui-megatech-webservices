package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.service.OrderService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Orders Management REST API")
public class OrderRestController {
    private final OrderService orderService;

    @Operation(summary = "Get an list Orders by UserId")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Order.GET_LIST_BY_USER_ID)
    public ResponseEntity<?> getListOrdersByUserId(
            @RequestBody ListOrdersForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(orderService.getListOrderForUser(requestDTO));
    }

    @Operation(summary = "Get an list Orders for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Order.GET_LIST_FOR_ADMIN)
    public ResponseEntity<?> getListOrdersByUserId(
            @RequestBody PaginationDTO.Request requestDTO
    ) {
        return ResponseUtil.ok(orderService.getListOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Get an Detail Orders for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Order.GET_DETAIL_FOR_USER)
    public ResponseEntity<?> getDetailOrderFoUser(
            @RequestBody OrderItemForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(orderService.getOrderDetailForUser(requestDTO));
    }

    @Operation(summary = "Get an Detail Orders for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.Order.GET_DETAIL_FOR_ADMIN)
    public ResponseEntity<?> getDetailOrderForAdmin(
            @PathVariable(name = "orderId") Integer orderId
    ) {
        return ResponseUtil.ok(orderService.getOrderDetailForAdmin(orderId));
    }

    @Operation(summary = "Add an Order for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.Order.ADD_ONE_FOR_USER)
    public ResponseEntity<?> getDetailOrderForUser(
            @RequestBody AddOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(orderService.addOrderForUser(requestDTO));
    }

    @Operation(summary = "Add an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.Order.ADD_ONE_FOR_ADMIN)
    public ResponseEntity<?> getDetailOrderForAdmin(
            @RequestBody AddOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(orderService.addOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Update an Order for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.Order.UPDATED_ONE_FOR_UER)
    public ResponseEntity<?> updateOrderForUser(
            @RequestBody ModifyOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(orderService.updateOrderForUser(requestDTO));
    }

    @Operation(summary = "Update an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.Order.UPDATED_ONE_FOR_ADMIN)
    public ResponseEntity<?> updateOrderForAdmin(
            @RequestBody ModifyOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(orderService.updateOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Delete an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(Endpoint.Order.DELETE_ONE_ORDER)
    public ResponseEntity<?> deleteOrderForAdmin(
            @PathVariable(name = "orderId") int orderId
    ) {
        return ResponseUtil.ok(orderService.deleteOrderForAdmin(orderId));
    }
}
