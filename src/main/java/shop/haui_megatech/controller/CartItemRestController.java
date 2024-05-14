package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.service.CartItemService;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Carts Management REST API")
@SecurityRequirement(name = "bearerAuth")
public class CartItemRestController {
    private final CartItemService cartItemService;

    @PostMapping(Endpoint.CartItem.ADD_ONE)
    public CommonResponseDTO<?> addCartItem(
            @PathVariable Integer productId,
            @RequestBody CartItemRequestDTO request
    ) {
        return cartItemService.addOne(productId, request);
    }

    @GetMapping(Endpoint.CartItem.GET_LIST_BY_USER)
    public PaginationResponseDTO<BriefCartItemResponseDTO> getListByUser(
            @PathVariable Integer userId,
            @ParameterObject PaginationRequestDTO request
    ) {
        return cartItemService.getListByUser(userId, request);
    }

    @PutMapping(Endpoint.CartItem.UPDATE_ONE)
    public CommonResponseDTO<?> updateCartItem(
            @PathVariable Integer productId,
            @PathVariable Integer cartItemId,
            @RequestBody CartItemRequestDTO request
    ) {
        return cartItemService.updateOne(productId, cartItemId, request);
    }

    @DeleteMapping(Endpoint.CartItem.DELETE)
    public CommonResponseDTO<?> deleteCartItems(
            @RequestParam String ids
    ) {
        return cartItemService.hardDeleteList(
                ListIdsRequestDTO
                        .builder()
                        .ids(List.of(ids.split(","))
                                 .parallelStream()
                                 .map(Integer::parseInt)
                                 .toList()
                        )
                        .build()
        );
    }
}
