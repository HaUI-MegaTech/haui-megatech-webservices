package shop.haui_megatech.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.haui_megatech.repository.BrandRepository;
import shop.haui_megatech.repository.ProductRepository;

@RequiredArgsConstructor
@Component
public class BrandClassifier {
    private final ProductRepository productRepository;
    private final BrandRepository   brandRepository;

//    public void classify() {
//        List<Product> products = productRepository.findAll();
//        List<Brand> brands = brandRepository.findAll();
//        for (Product product : products) {
//            for (Brand brand : brands) {
//                if (product.getName().contains(brand.getName())) {
//                    product.setBrandId(brand.getId());
//                }
//            }
//        }
//        productRepository.saveAll(products);
//    }
}
