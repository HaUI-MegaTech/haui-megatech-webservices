package shop.haui_megatech.validator;

import org.apache.logging.log4j.util.Strings;

public class RequestValidator {

    public static boolean isBlankRequestParams(Object ...params) {
        for (Object param : params) {
            if (param == null || Strings.isEmpty((String) param)) return false;
        }
        return true;
    }

    public static boolean isMatchPasswordPair(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
