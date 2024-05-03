package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.cart.GetCartItemsRequestDTO;
import shop.haui_megatech.domain.dto.cart.GetCartItemsResponseDTO;
import shop.haui_megatech.domain.dto.cart.ModifyCartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.common.RequestIdDTO;
import shop.haui_megatech.service.CartService;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Carts Management REST API")
@SecurityRequirement(name = "bearerAuth")
public class CartRestController {
    private final CartService cartService;

    @PostMapping(UrlConstant.CartItem.ADD_ONE)
    public CommonResponseDTO<?> addCartItem(
            @PathVariable(UrlConstant.PathVariableName.LOGINED_USER_ID) Integer loginedUserId,
            @RequestBody ModifyCartItemRequestDTO request
    ) {
        return cartService.addOne(request);
    }

    @GetMapping(UrlConstant.CartItem.GET_LIST)
    public GetCartItemsResponseDTO getCartItems(
            @RequestBody GetCartItemsRequestDTO request
    ) {
        return cartService.getCartItems(request);
    }

    @PutMapping(UrlConstant.CartItem.UPDATE_CART_ITEM)
    public CommonResponseDTO<?> updateCartItem(
            @PathVariable(UrlConstant.PathVariableName.LOGINED_USER_ID) Integer loginedUserId,
            @PathVariable(UrlConstant.PathVariableName.CART_ITEM_ID) Integer cartItemId,
            @RequestBody ModifyCartItemRequestDTO request
    ) {
        return cartService.updateOne(request.cartItemId(), request);
    }

    @DeleteMapping(UrlConstant.CartItem.DELETE_ONE)
    public CommonResponseDTO<?> deleteCartItem(
            @PathVariable(UrlConstant.PathVariableName.LOGINED_USER_ID) Integer loginedUserId,
            @PathVariable(UrlConstant.PathVariableName.CART_ITEM_ID) Integer cartItemId,
            @RequestBody RequestIdDTO request
    ) {
        return cartService.hardDeleteOne(request);
    }

    @DeleteMapping(UrlConstant.CartItem.DELETE_LIST)
    public CommonResponseDTO<?> deleteCartItems(
            @PathVariable(UrlConstant.PathVariableName.LOGINED_USER_ID) Integer loginedUserId,
            @RequestBody ListIdsRequestDTO request
    ) {
        return cartService.hardDeleteList(request);
    }
}
