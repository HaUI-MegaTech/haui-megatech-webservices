package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.order.AddOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.AddOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.order.ModifyOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.ModifyOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.service.OrderService;
import shop.haui_megatech.utility.ResponseUtil;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Orders Management REST API")
public class OrderRestController {
    private final OrderService OrderService;

    @Operation(summary = "Get an list Orders for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_LIST_BY_USER_ID)
    public ResponseEntity<?> getListOrdersByUserIdForUser(
            @ParameterObject PaginationRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.getListOrderForUser(requestDTO));
    }

    @Operation(summary = "Get an list Orders for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_LIST_FOR_ADMIN)
    public ResponseEntity<?> getListOrdersByUserIdforAdmin(
            @ParameterObject PaginationRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.getListOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Get an Detail Orders for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_DETAIL_FOR_USER)
    public ResponseEntity<?> getDetailOrderForUser(
            @PathVariable(name = "OrderId") Integer OrderId
    ) {
        return ResponseUtil.ok(OrderService.getOrderDetailForUser(OrderId));
    }

    @Operation(summary = "Get an Detail Orders for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_DETAIL_FOR_ADMIN)
    public ResponseEntity<?> getDetailOrderForAdmin(
            @PathVariable(name = "OrderId") Integer OrderId
    ) {
        return ResponseUtil.ok(OrderService.getOrderDetailForAdmin(OrderId));
    }

    @Operation(summary = "Add an Order for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.V1.Order.ADD_ONE_FOR_USER)
    public ResponseEntity<?> addOrderForUser(
            @RequestBody AddOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.addOrderForUser(requestDTO));
    }

    @Operation(summary = "Add an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PostMapping(Endpoint.V1.Order.ADD_ONE_FOR_ADMIN)
    public ResponseEntity<?> addOrderForAdmin(
            @RequestBody AddOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.addOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Update an Order for User")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.V1.Order.UPDATED_ONE_FOR_UER)
    public ResponseEntity<?> updateOrderForUser(
            @RequestBody ModifyOrderForUserRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.updateOrderForUser(requestDTO));
    }

    @Operation(summary = "Update an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @PutMapping(Endpoint.V1.Order.UPDATED_ONE_FOR_ADMIN)
    public ResponseEntity<?> updateOrderForAdmin(
            @RequestBody ModifyOrderForAdminRequestDTO requestDTO
    ) {
        return ResponseUtil.ok(OrderService.updateOrderForAdmin(requestDTO));
    }

    @Operation(summary = "Delete an Order for Admin")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @DeleteMapping(Endpoint.V1.Order.DELETE_ONE_ORDER)
    public ResponseEntity<?> deleteOrderForAdmin(
            @PathVariable(name = "OrderId") int OrderId
    ) {
        return ResponseUtil.ok(OrderService.deleteOrderForAdmin(OrderId));
    }
}
