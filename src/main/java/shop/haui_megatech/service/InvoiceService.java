package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.order.*;

public interface InvoiceService {
    PaginationDTO.Response<?> getListInvoiceForUser(PaginationDTO.Request requestDTO);

    PaginationDTO.Response<?> getListInvoiceForAdmin(PaginationDTO.Request requestDTO);

    CommonResponseDTO<OrderItemResponseDTO> getInvoiceDetailForUser(Integer userId);

    CommonResponseDTO<OrderItemResponseDTO> getInvoiceDetailForAdmin(Integer orderId);

    CommonResponseDTO<OrderBaseDTO> addInvoiceForUser(AddOrderForUserRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> addInvoiceForAdmin(AddOrderForAdminRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> updateInvoiceForUser(ModifyOrderForUserRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> updateInvoiceForAdmin(ModifyOrderForAdminRequestDTO requestDTO);

    CommonResponseDTO<OrderBaseDTO> deleteInvoiceForAdmin(int orderId);
}
