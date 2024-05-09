package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.order.*;

public interface OrderService {
    PaginationDTO.Response<?> getListOrderForUser(PaginationDTO.Request requestDTO);

    PaginationDTO.Response<?> getListOrderForAdmin(PaginationDTO.Request requestDTO);

    CommonResponseDTO<OrderItemResponseDTO> getOrderDetailForUser(Integer userId);

    CommonResponseDTO<OrderItemResponseDTO> getOrderDetailForAdmin(Integer orderId);

    CommonResponseDTO<OrderBaseDTO> addOrderForUser(AddOrderForUserRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> addOrderForAdmin(AddOrderForAdminRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> updateOrderForUser(ModifyOrderForUserRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> updateOrderForAdmin(ModifyOrderForAdminRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> deleteOrderForAdmin(int orderId);
}
