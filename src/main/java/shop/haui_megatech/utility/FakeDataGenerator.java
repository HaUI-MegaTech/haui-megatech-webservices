package shop.haui_megatech.utility;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.repository.ProductRepository;

import java.util.List;

@Component
@AllArgsConstructor
public class FakeDataGenerator {
    private final ProductRepository productRepository;

    public void fakeProductData() {
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> {
            product.setImported(900);
            product.setImportPrice(product.getOldPrice() * RandomValueGenerator.getRandomValue(0.7f, 0.8f));
            product.setTotalSold(RandomValueGenerator.getRandomValue(20, 100));
            product.setRemaining(product.getImported() - product.getTotalSold());
        });
        productRepository.saveAll(allProducts);
    }
}
