package shop.haui_megatech.utility;

public class RandomValueGenerator {
    public static int getRandomValue(int begin, int end) {
        return (int) (Math.random() * (end - begin)) + begin;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(getRandomValue(i, 1000));
        }
    }
}
