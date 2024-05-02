package shop.haui_megatech.utility;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class DecimalFormatUtil {
    private final DecimalFormat decimalFormat;

    public String format(Object data) {
        return data == null ? Strings.EMPTY : decimalFormat.format(data);
    }
}
