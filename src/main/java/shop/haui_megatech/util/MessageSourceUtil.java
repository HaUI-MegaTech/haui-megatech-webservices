package shop.haui_megatech.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSourceUtil {
    private final MessageSource messageSource;

    public String getMessage(String message) {
        return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
    }
}
