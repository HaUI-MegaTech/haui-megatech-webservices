package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.service.base.HardDeletable;

import java.util.List;

public interface CartItemService extends HardDeletable {
    GlobalResponseDTO<?> addOne(Integer productId, CartItemRequestDTO request);

    GlobalResponseDTO<?> updateOne(Integer productId, Integer cartItemId, CartItemRequestDTO request);

    GlobalResponseDTO<List<BriefCartItemResponseDTO>> getListByUser(PaginationRequestDTO request);
}
