package shop.haui_megatech.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    CUSTOMER(1),
    STAFF(2),
    MANAGER(3),
    ADMIN(4),
    PRESIDENT(5);

    private final Integer priority;
}
