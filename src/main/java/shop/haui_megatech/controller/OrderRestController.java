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
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.order.*;
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
            @PathVariable(name = "orderId") Integer orderId
    ) {
        return ResponseUtil.ok(OrderService.getOrderDetailForUser(orderId));
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
            @PathVariable(name = "orderId") Integer orderId
    ) {
        return ResponseUtil.ok(OrderService.getOrderDetailForAdmin(orderId));
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
            @PathVariable(name = "orderId") int orderId
    ) {
        return ResponseUtil.ok(OrderService.deleteOrderForAdmin(orderId));
    }
    @Operation(summary = "Get statistic order by month")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_STATISTIC_BY_MONTH)
    public ResponseEntity<?> getStatisticByMonth(
//            @RequestParam("month") int month,
//            @RequestParam("year") int year
            ) {

        return ResponseUtil.ok(OrderService.statisticByMonth(3, 2022));
    }
    @Operation(summary = "Get statistic order by administrative region")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "404", description = "Not Found")
            }
    )
    @GetMapping(Endpoint.V1.Order.GET_STATISTIC_BY_ADMIN_REGION)
    public ResponseEntity<?> getStatisticAdminRegion(
//            @RequestParam(name="year") int year
    ) {
        return ResponseUtil.ok(OrderService.statisticByAdminRegion(2022));
    }
}
