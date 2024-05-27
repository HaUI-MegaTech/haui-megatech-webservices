package shop.haui_megatech;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Slf4j
public class HauiMegatechWebservicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(HauiMegatechWebservicesApplication.class, args);
        log.info("http://localhost:8080/swagger-ui/index.html");
    }

}
