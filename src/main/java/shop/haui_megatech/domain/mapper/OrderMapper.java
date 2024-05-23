package shop.haui_megatech.domain.mapper;

import shop.haui_megatech.domain.dto.order.AddOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.AddOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.order.OrderBaseDTO;
import shop.haui_megatech.domain.dto.order.OrderItemResponseDTO;
import shop.haui_megatech.domain.entity.Order;

public interface OrderMapper {
    Order addOrderRequestForUserDTOtoOrder(AddOrderForUserRequestDTO requestDTO);

    Order addOrderRequestForAdminDTOtoOrder(AddOrderForAdminRequestDTO requestDTO);

    OrderBaseDTO orderToOrderBase(Order order);

    OrderItemResponseDTO orderItemResponseDto(Order order);
}
