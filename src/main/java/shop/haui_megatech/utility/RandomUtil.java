package shop.haui_megatech.utility;

public class RandomUtil {

    public static String randomPassword() {
        return Integer.toString((int) (Math.random() * 899_999 + 100_000));
    }
}
