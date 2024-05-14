package shop.haui_megatech.domain.dto.order;

import jakarta.persistence.Column;
import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.entity.Address;
import shop.haui_megatech.domain.entity.enums.OrderStatus;
import shop.haui_megatech.domain.entity.enums.PaymentMethod;

import java.util.List;

public record AddOrderForUserRequestDTO(
        @Column(name = "sub_total")
        float subTotal,
        float tax,
        @Column(name = "payment_method")
        PaymentMethod paymentMethod,
        @Column(name = "order_weight")
        float orderWeight,
        Address address,
        OrderStatus status,
        List<OrderDetailRequestDTO> orderDetailRequestDTOList
) {
}
