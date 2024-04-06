package shop.haui_megatech.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.repository.ProductRepository;

@Configuration
public class MyApplicationConfig {
	@Autowired
	private ProductRepository repo;

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			for (int i = 1; i <= 100; i++) {
				repo.save(Product.builder().name("San pham " + i).price((float) Math.random() * 1000).build());
			}

		};
	}
}
