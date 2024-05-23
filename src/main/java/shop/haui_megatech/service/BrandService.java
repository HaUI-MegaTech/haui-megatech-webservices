package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.brand.BrandResponseDTO;
import shop.haui_megatech.domain.dto.brand.BrandStatisticResponseDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.service.base.Gettable;

import java.util.List;

public interface BrandService extends Gettable<List<BrandResponseDTO>> {
    GlobalResponseDTO<List<BrandStatisticResponseDTO>> getTotalRevenue();

    GlobalResponseDTO<List<BrandStatisticResponseDTO>> getTotalSold();

    GlobalResponseDTO<List<BrandStatisticResponseDTO>> getTotalView();
}
