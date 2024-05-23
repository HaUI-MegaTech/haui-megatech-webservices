package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;

public interface OrderService {
    GlobalResponseDTO<?> getListOrderForUser(PaginationRequestDTO requestDTO);

    GlobalResponseDTO<?> getListOrderForAdmin(PaginationRequestDTO requestDTO);

    GlobalResponseDTO<OrderItemResponseDTO> getOrderDetailForUser(Integer userId);

    GlobalResponseDTO<OrderItemResponseDTO> getOrderDetailForAdmin(Integer orderId);

    GlobalResponseDTO<OrderBaseDTO> addOrderForUser(AddOrderForUserRequestDTO requestDTO);

    GlobalResponseDTO<OrderBaseDTO> addOrderForAdmin(AddOrderForAdminRequestDTO requestDTO);

    GlobalResponseDTO<OrderBaseDTO> updateOrderForUser(ModifyOrderForUserRequestDTO requestDTO);

    GlobalResponseDTO<OrderBaseDTO> updateOrderForAdmin(ModifyOrderForAdminRequestDTO requestDTO);

    GlobalResponseDTO<OrderBaseDTO> deleteOrderForAdmin(int orderId);
}
