package shop.haui_megatech.domain.dto.order;

import lombok.Builder;
import shop.haui_megatech.domain.entity.OrderStatus;
import shop.haui_megatech.domain.entity.PaymentMethod;

import java.util.Date;

@Builder
public record OrderBaseDTO(
        int id,
        String firstName,
        String lastName,
        String address,
        PaymentMethod paymentMethod,
        Date orderTime,
        OrderStatus orderStatus,
        float total
) {
}
