package shop.haui_megatech.utility;

import org.springframework.security.core.context.SecurityContextHolder;
import shop.haui_megatech.domain.entity.User;

public class AuthenticationUtil {

    public static User getRequestedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
