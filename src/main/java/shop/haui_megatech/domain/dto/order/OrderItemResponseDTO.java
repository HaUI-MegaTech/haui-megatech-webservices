package shop.haui_megatech.domain.dto.order;

import lombok.Builder;
import shop.haui_megatech.domain.dto.order_detail.OrderOrderDetailResponseDTO;
import shop.haui_megatech.domain.entity.OrderStatus;
import shop.haui_megatech.domain.entity.PaymentMethod;

import java.util.Date;
import java.util.List;
@Builder
public record OrderItemResponseDTO(
        String shippingCost,
        String subTotal,
        String tax,
        String total,
        PaymentMethod paymentMethod,
        Date payTime,
        Date orderTime,
        Date deliverTime,
        float orderWeight,
        String address,
        OrderStatus status,
        List<OrderOrderDetailResponseDTO> orderOrderDetailResponseDTO
) {
}
