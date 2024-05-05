package shop.haui_megatech.domain.dto.order;

import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.entity.OrderStatus;
import shop.haui_megatech.domain.entity.PaymentMethod;

import java.util.Date;
import java.util.List;

public record AddOrderForUserRequestDTO(
        String token,
        float shippingCost,
        float subTotal,
        float tax,
        float total,
        PaymentMethod paymentMethod,
        Date payTime,
        Date orderTime,
        Date deliverTime,
        float orderWeight,
        String address,
        OrderStatus status,
        List<OrderDetailRequestDTO> orderDetailRequestDTOList
) {
}
