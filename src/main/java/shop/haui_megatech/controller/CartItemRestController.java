package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.constant.Endpoint;
import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.service.CartItemService;

import java.util.List;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "Carts Management REST API")
@SecurityRequirement(name = "bearerAuth")
public class CartItemRestController {
    private final CartItemService cartItemService;


    @Operation(summary = "API Add a CartItem")
    @PostMapping(Endpoint.V1.CartItem.ADD_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> addCartItem(
            @PathVariable Integer productId,
            @RequestBody CartItemRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(cartItemService.addOne(productId, request));
    }

    @Operation(summary = "API Get a list of CartItems by User Id ")
    @GetMapping(Endpoint.V1.CartItem.GET_LIST)
    public ResponseEntity<GlobalResponseDTO<PaginatedMeta, List<BriefCartItemResponseDTO>>> getListByUser(
            @ParameterObject PaginationRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(cartItemService.getListByUser(request));
    }


    @Operation(summary = "API Update a CartItem")
    @PutMapping(Endpoint.V1.CartItem.UPDATE_ONE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> updateCartItem(
            @PathVariable Integer productId,
            @PathVariable Integer cartItemId,
            @RequestBody CartItemRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(cartItemService.updateOne(productId, cartItemId, request));
    }

    @Operation(summary = "API Delete a CartItem")
    @DeleteMapping(Endpoint.V1.CartItem.DELETE)
    public ResponseEntity<GlobalResponseDTO<NoPaginatedMeta, BlankData>> deleteCartItems(
            @PathVariable String cartItemIds
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(cartItemService.hardDeleteList(
                ListIdsRequestDTO
                        .builder()
                        .ids(List.of(cartItemIds.split(","))
                                 .parallelStream()
                                 .map(Integer::parseInt)
                                 .toList()
                        )
                        .build()
        ));
    }
}
