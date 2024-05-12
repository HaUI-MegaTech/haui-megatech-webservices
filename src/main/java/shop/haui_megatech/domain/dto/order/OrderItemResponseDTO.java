package shop.haui_megatech.domain.dto.order;

import lombok.Builder;
import shop.haui_megatech.domain.dto.order_detail.OrderOrderDetailResponseDTO;
import shop.haui_megatech.domain.entity.enums.OrderStatus;
import shop.haui_megatech.domain.entity.enums.PaymentMethod;

import java.util.Date;
import java.util.List;

@Builder
public record OrderItemResponseDTO(
        int orderId,
        String shippingCost,
        String subTotal,
        String tax,
        String total,
        PaymentMethod paymentMethod,
        Date payTime,
        Date orderTime,
        int deliverTime,
        float orderWeight,
        String address,
        OrderStatus status,
        List<OrderOrderDetailResponseDTO> orderOrderDetailResponseDTO
) {
}
