package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.global.PaginatedMeta;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.order.*;

import java.util.List;
import java.util.Map;

public interface OrderService {
    GlobalResponseDTO<PaginatedMeta, List<OrderBaseDTO>> getListOrderForUser(PaginationRequestDTO requestDTO);

    GlobalResponseDTO<PaginatedMeta, List<OrderBaseDTO>> getListOrderForAdmin(PaginationRequestDTO requestDTO);

    GlobalResponseDTO<NoPaginatedMeta, OrderItemResponseDTO> getOrderDetailForUser(Integer userId);

    GlobalResponseDTO<NoPaginatedMeta, OrderItemResponseDTO> getOrderDetailForAdmin(Integer orderId);

    GlobalResponseDTO<NoPaginatedMeta, OrderBaseDTO> addOrderForUser(AddOrderForUserRequestDTO requestDTO);

    GlobalResponseDTO<NoPaginatedMeta, OrderBaseDTO> addOrderForAdmin(AddOrderForAdminRequestDTO requestDTO);

    GlobalResponseDTO<NoPaginatedMeta, OrderBaseDTO> updateOrderForUser(ModifyOrderForUserRequestDTO requestDTO);

    GlobalResponseDTO<NoPaginatedMeta, OrderBaseDTO> updateOrderForAdmin(ModifyOrderForAdminRequestDTO requestDTO);

    GlobalResponseDTO<NoPaginatedMeta, OrderBaseDTO> deleteOrderForAdmin(int orderId);
    GlobalResponseDTO<NoPaginatedMeta, Map<String, List<Object>>> statisticByMonth(int month, int year);

    GlobalResponseDTO<NoPaginatedMeta, Map<String, List<Object>>> statisticByAdminRegion(int year);
}
