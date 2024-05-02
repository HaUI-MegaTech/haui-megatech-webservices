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
import shop.haui_megatech.domain.dto.common.RequestIdDTO;
import shop.haui_megatech.service.CartService;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Carts Management REST API")
@SecurityRequirement(name = "bearerAuth")
public class CartRestController {
    private final CartService cartService;

    @PostMapping(UrlConstant.Cart.ADD_CART_ITEM)
    public CommonResponseDTO<?> addCartItem(
            @RequestBody ModifyCartItemRequestDTO request
    ) {
        return cartService.addOne(request);
    }

    @GetMapping(UrlConstant.Cart.GET_CART_ITEMS)
    public GetCartItemsResponseDTO getCartItems(
            @RequestBody GetCartItemsRequestDTO request
    ) {
        return cartService.getCartItems(request);
    }

    @PutMapping(UrlConstant.Cart.UPDATE_CART_ITEM)
    public CommonResponseDTO<?> updateCartItem(
            @RequestBody ModifyCartItemRequestDTO request
    ) {
        return cartService.updateOne(request.cartItemId(), request);
    }

    @DeleteMapping(UrlConstant.Cart.DELETE_CART_ITEM)
    public CommonResponseDTO<?> deleteCartItem(
            @RequestBody RequestIdDTO request
    ) {
        return cartService.hardDeleteOne(request);
    }
}
