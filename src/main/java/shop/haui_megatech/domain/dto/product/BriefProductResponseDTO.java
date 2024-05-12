package shop.haui_megatech.domain.dto.product;

import lombok.Builder;

@Builder
public record BriefProductResponseDTO(
        Integer id,
        String name,
        Float oldPrice,
        Float newPrice,
        String display,
        String processor,
        String card,
        String battery,
        String weight,
        Integer discountPercent,
        String ram,
        String storage,
        String mainImageUrl
) {
}
