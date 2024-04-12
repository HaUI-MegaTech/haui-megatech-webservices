package shop.haui_megatech.domain.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.dto.product.ProductDTO;

import java.text.DecimalFormat;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {
    private final DecimalFormat df;

    @Override
    public ProductDTO toProductDTO(Product product) {
        return ProductDTO.builder()
                         .id(product.getId())
                         .name(product.getName())
                         .oldPrice(df.format(product.getOldPrice()))
                         .newPrice(df.format(product.getNewPrice()))
                         .display(product.getDisplay())
                         .processor(product.getProcessor())
                         .card(product.getCard())
                         .battery(product.getBattery())
                         .weight(df.format(product.getWeight()))
                         .discountPercent(product.getDiscountPercent() == null
                                                  ? ""
                                                  : product.getDiscountPercent() + "%")
                         .ram(product.getRam() + " GB")
                         .storage(product.getStorage())
                         .bannerImg(product.getBannerImg())
                         .build();
    }

    @Override
    public Product toProduct(ProductDTO productDTO) {
        return Product.builder()
                      .name(productDTO.name())
                      .oldPrice(Float.parseFloat(productDTO.oldPrice()))
                      .build();
    }

}
