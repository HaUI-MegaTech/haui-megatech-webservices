package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.cart.CartItemDTO;
import shop.haui_megatech.domain.dto.cart.ModifyCartItemRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.service.base.Addable;
import shop.haui_megatech.service.base.HardDeletable;
import shop.haui_megatech.service.base.Updatable;

public interface CartItemService extends Addable<ModifyCartItemRequestDTO>,
                                         Updatable<ModifyCartItemRequestDTO>,
                                         HardDeletable {
    PaginationResponseDTO<CartItemDTO> getCartItems(PaginationRequestDTO request);
}
