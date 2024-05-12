package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.service.base.Addable;
import shop.haui_megatech.service.base.HardDeletable;
import shop.haui_megatech.service.base.Updatable;

public interface CartItemService extends Addable<CartItemRequestDTO>,
                                         Updatable<CartItemRequestDTO>,
                                         HardDeletable {
    PaginationResponseDTO<BriefCartItemResponseDTO> getListByUser(Integer userId, PaginationRequestDTO request);
}
