package shop.haui_megatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HauiMegatechWebservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HauiMegatechWebservicesApplication.class, args);
        System.out.println("http://localhost:8080/swagger-ui/index.html#");
    }

}
