package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.UrlConstant;
import shop.haui_megatech.domain.dto.CartItemDTO;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.service.CartItemService;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Carts Management REST API")
@SecurityRequirement(name = "bearerAuth")
public class CartItemRestController {
    private final CartItemService cartItemService;

    @PostMapping(UrlConstant.CartItem.ADD_ONE)
    public CommonResponseDTO<?> addCartItem(
            @RequestBody CartItemDTO.Request request
    ) {
        return cartItemService.addOne(request);
    }

    @GetMapping(UrlConstant.CartItem.GET_LIST)
    public PaginationDTO.Response<CartItemDTO.Response> getCartItems(
            @ParameterObject PaginationDTO.Request request
    ) {
        return cartItemService.getCartItems(request);
    }

    @PutMapping(UrlConstant.CartItem.UPDATE_ONE)
    public CommonResponseDTO<?> updateCartItem(
            @PathVariable Integer cartItemId,
            @RequestBody CartItemDTO.Request request
    ) {
        return cartItemService.updateOne(cartItemId, request);
    }

    @DeleteMapping(UrlConstant.CartItem.DELETE)
    public CommonResponseDTO<?> deleteCartItems(
            @PathVariable String cartItemIds
    ) {
        return cartItemService.hardDeleteList(
                ListIdsRequestDTO.builder()
                                 .ids(List.of(cartItemIds.split(","))
                                          .parallelStream()
                                          .map(Integer::parseInt)
                                          .toList()
                                 )
                                 .build()
        );
    }
}
