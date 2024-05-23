package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.brand.BrandResponseDTO;
import shop.haui_megatech.domain.dto.brand.BrandStatisticResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.service.base.Gettable;

public interface BrandService extends Gettable<BrandResponseDTO> {
    NoPaginationResponseDTO<BrandStatisticResponseDTO> getTotalRevenue();

    NoPaginationResponseDTO<BrandStatisticResponseDTO> getTotalSold();
}
