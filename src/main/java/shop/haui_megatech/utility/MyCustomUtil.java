package shop.haui_megatech.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.haui_megatech.domain.entity.Feedback;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.repository.ProductRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MyCustomUtil {
    private final ProductRepository productRepository;

    public void calculateProductRatingAverage() {
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> {
            int size = product.getFeedbacks().size();
            int totalRating = product.getFeedbacks().stream().mapToInt(Feedback::getRating).sum();
            float averageRating = (float) totalRating / size;
            product.setAverageRating(averageRating);
        });
        productRepository.saveAll(allProducts);
    }

    public void countProductFeedbacks() {
        List<Product> allProducts = productRepository.findAll();
        allProducts.forEach(product -> {
            int size = product.getFeedbacks().size();
            product.setFeedbacksCount(size);
        });
        productRepository.saveAll(allProducts);
    }
}
