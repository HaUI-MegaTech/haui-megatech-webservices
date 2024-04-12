package shop.haui_megatech.domain.dto.product;

import lombok.Builder;

@Builder
public record ProductDTO(
        Integer id,
        String name,
        String oldPrice,
        String newPrice,
        String display,
        String processor,
        String card,
        String battery,
        String weight,
        Integer discountPercent,
        Integer ram,
        String storage,
        String bannerImg
) {
}
