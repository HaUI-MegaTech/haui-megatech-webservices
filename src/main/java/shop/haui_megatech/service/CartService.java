package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.cart.GetCartItemsRequestDTO;
import shop.haui_megatech.domain.dto.cart.GetCartItemsResponseDTO;
import shop.haui_megatech.domain.dto.cart.ModifyCartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.RequestIdDTO;
import shop.haui_megatech.service.base.Addable;
import shop.haui_megatech.service.base.HardDeletable;
import shop.haui_megatech.service.base.Updatable;

public interface CartService extends Addable<ModifyCartItemRequestDTO>,
                                     Updatable<ModifyCartItemRequestDTO>,
                                     HardDeletable<RequestIdDTO> {
    GetCartItemsResponseDTO getCartItems(GetCartItemsRequestDTO request);
}
