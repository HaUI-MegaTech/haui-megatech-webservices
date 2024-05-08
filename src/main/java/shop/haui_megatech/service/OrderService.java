package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

import java.util.Optional;

public interface OrderService
{
    PaginationResponseDTO<?> getListOrderForUser (PaginationRequestDTO requestDTO);
    PaginationResponseDTO<?> getListOrderForAdmin (PaginationRequestDTO requestDTO);
    CommonResponseDTO<OrderItemResponseDTO> getOrderDetailForUser (int orderId);
    CommonResponseDTO<OrderItemResponseDTO> getOrderDetailForAdmin (Integer orderId);
    CommonResponseDTO<OrderBaseDTO> addOrderForUser(AddOrderForUserRequestDTO requestDTO);
    CommonResponseDTO<OrderBaseDTO> addOrderForAdmin(AddOrderForAdminRequestDTO requestDTO);
    CommonResponseDTO<OrderBaseDTO> updateOrderForUser(ModifyOrderForUserRequestDTO requestDTO);
    CommonResponseDTO<OrderBaseDTO> updateOrderForAdmin(ModifyOrderForAdminRequestDTO requestDTO);
    CommonResponseDTO<OrderBaseDTO> deleteOrderForAdmin(int orderId);
}
