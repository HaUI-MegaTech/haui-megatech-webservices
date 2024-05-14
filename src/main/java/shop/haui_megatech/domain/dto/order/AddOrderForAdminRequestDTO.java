package shop.haui_megatech.domain.dto.order;

import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.entity.Address;
import shop.haui_megatech.domain.entity.enums.OrderStatus;
import shop.haui_megatech.domain.entity.enums.PaymentMethod;

import java.util.Date;
import java.util.List;

public record AddOrderForAdminRequestDTO(
        int userId,
        float shippingCost,
        float subTotal,
        float tax,
        float total,
        PaymentMethod paymentMethod,
        Date payTime,
        Date orderTime,
        int deliverTime,
        float orderWeight,
        Address address,
        OrderStatus status,
        List<OrderDetailRequestDTO> orderDetailRequestDTOList
) {
}
