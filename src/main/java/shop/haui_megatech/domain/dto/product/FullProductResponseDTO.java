package shop.haui_megatech.domain.dto.product;

import lombok.Builder;
import shop.haui_megatech.domain.dto.ImageDTO;
import shop.haui_megatech.domain.entity.SimilarProduct;

import java.util.List;

@Builder
public record FullProductResponseDTO(
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

        BrandInFullProductResponseDTO brand,

        String article,

        List<ImageDTO> images,

        List<SimilarProduct> similarProducts
) {
}
