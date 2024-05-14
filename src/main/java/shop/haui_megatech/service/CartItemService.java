package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.service.base.Addable;
import shop.haui_megatech.service.base.HardDeletable;
import shop.haui_megatech.service.base.Updatable;

public interface CartItemService extends HardDeletable {
    CommonResponseDTO<?> addOne(Integer productId, CartItemRequestDTO request);

    CommonResponseDTO<?> updateOne(Integer productId, Integer cartItemId, CartItemRequestDTO request);

    PaginationResponseDTO<BriefCartItemResponseDTO> getListByUser(Integer userId, PaginationRequestDTO request);
}
