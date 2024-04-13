package shop.haui_megatech;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class HauiMegatechWebservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HauiMegatechWebservicesApplication.class, args);
		System.out.println("http://localhost:8080/swagger-ui/index.html#");
	}

}
