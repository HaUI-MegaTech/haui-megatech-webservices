package shop.haui_megatech.domain.dto;

import lombok.Builder;
import shop.haui_megatech.domain.entity.SimilarProduct;

import java.util.List;

public record ProductDTO() {

    @Builder
    public record SummaryResponse(
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
    ) {}

    @Builder
    public record MinimizedResponse(
            String name,
            String mainImageUrl
    ) {}

    @Builder
    public record FullResponse(
            Integer id,
            String name,
            Float oldPrice,
            Float currentPrice,
            Integer discountPercent,

            String processor,
            Integer cores,
            Integer threads,
            String frequency,
            String boostFrequency,
            String cacheCapacity,

            String memoryCapacity,
            String memoryType,
            String memoryBus,
            String maxMemoryCapacity,
            String storage,

            String displaySize,
            String resolution,
            String refreshRate,
            String colorGamut,
            String panelType,
            String touchScreen,

            String graphicsCard,
            String soundTechnology,

            String wirelessConnectivity,
            String sdCard,
            String webcam,
            String coolingFan,
            String miscFeature,
            String backlitKeyboard,

            String dimensionWeight,
            String material,

            String batteryCapacity,
            String chargerCapacity,
            String os,
            Integer launchDate,

            ProductDTO.BrandResponse brand,

            String article,

            List<ImageDTO> images,

            List<SimilarProduct> similarProducts
    ) {}

    public record BrandResponse(
            Integer id,
            String name
    ) {}

    public record AddRequest(
            String name,
            Float price
    ) {}

    public record FilterRequest(
            String brandIds,
            Float minPrice,
            Float maxPrice
    ) {}

    public record UpdateRequest(
            String name,
            Float price
    ) {}


}
