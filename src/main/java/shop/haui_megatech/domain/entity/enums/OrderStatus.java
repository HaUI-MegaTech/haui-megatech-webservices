package shop.haui_megatech.domain.entity.enums;

import lombok.Getter;

public enum OrderStatus {
    NEW,
    CANCELLED,
    PROCESSING,
    PACKAGED,
    PICKED,
    SHIPPING,
    DELIVERED,
    RETURNED,
    PAID,
    REFUNDED;
}
