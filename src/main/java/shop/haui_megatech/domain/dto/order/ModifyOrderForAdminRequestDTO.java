package shop.haui_megatech.domain.dto.order;

import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.entity.OrderStatus;
import shop.haui_megatech.domain.entity.PaymentMethod;

import java.util.Date;
import java.util.List;

public record ModifyOrderForAdminRequestDTO(
        int orderId,
        AddOrderForAdminRequestDTO addOrderForAdminRequestDTO
) {
}
