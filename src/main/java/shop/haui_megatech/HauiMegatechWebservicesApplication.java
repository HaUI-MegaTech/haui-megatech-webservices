package shop.haui_megatech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class HauiMegatechWebservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HauiMegatechWebservicesApplication.class, args);
        System.out.println("http://localhost:8080/swagger-ui/index.html#");
    }

}
