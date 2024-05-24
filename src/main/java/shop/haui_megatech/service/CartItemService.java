package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.global.*;

import java.util.List;

public interface CartItemService {
    GlobalResponseDTO<NoPaginatedMeta, BlankData> addOne(Integer productId, CartItemRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> updateOne(Integer productId, Integer cartItemId, CartItemRequestDTO request);

    GlobalResponseDTO<PaginatedMeta, List<BriefCartItemResponseDTO>> getListByUser(PaginationRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteOne(Integer id);

    GlobalResponseDTO<NoPaginatedMeta, BlankData> hardDeleteList(ListIdsRequestDTO request);
}
