package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.CartItemDTO;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.service.base.Addable;
import shop.haui_megatech.service.base.HardDeletable;
import shop.haui_megatech.service.base.Updatable;

public interface CartItemService extends Addable<CartItemDTO.Request>,
                                         Updatable<CartItemDTO.Request>,
                                         HardDeletable {
    PaginationDTO.Response<CartItemDTO.Response> getListByUser(Integer userId, PaginationDTO.Request request);
}
