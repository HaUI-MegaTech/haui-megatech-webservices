package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;

public interface HomeService {
    NoPaginationResponseDTO<ProductCountByBrandResponseDTO> getProductCountByBrand();
}
