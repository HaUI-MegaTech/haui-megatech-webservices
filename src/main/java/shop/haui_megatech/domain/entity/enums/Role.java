package shop.haui_megatech.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    ROLE_CUSTOMER(1),
    ROLE_STAFF(2),
    ROLE_MANAGER(3),
    ROLE_ADMIN(4),
    ROLE_PRESIDENT(5);

    private final Integer priority;
}
