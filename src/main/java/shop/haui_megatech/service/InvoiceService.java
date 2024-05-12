package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;

public interface InvoiceService {
    PaginationResponseDTO<?> getListInvoiceForUser(PaginationRequestDTO requestDTO);

    PaginationResponseDTO<?> getListInvoiceForAdmin(PaginationRequestDTO requestDTO);

    CommonResponseDTO<OrderItemResponseDTO> getInvoiceDetailForUser(Integer userId);

    CommonResponseDTO<OrderItemResponseDTO> getInvoiceDetailForAdmin(Integer orderId);

    CommonResponseDTO<OrderBaseDTO> addInvoiceForUser(AddOrderForUserRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> addInvoiceForAdmin(AddOrderForAdminRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> updateInvoiceForUser(ModifyOrderForUserRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> updateInvoiceForAdmin(ModifyOrderForAdminRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> deleteInvoiceForAdmin(int orderId);
}
