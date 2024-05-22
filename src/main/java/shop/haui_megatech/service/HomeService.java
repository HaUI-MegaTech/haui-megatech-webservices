package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.TopSoldProductResponseDTO;

public interface HomeService {
    NoPaginationResponseDTO<ProductCountByBrandResponseDTO> getProductCountByBrand();

    NoPaginationResponseDTO<TopSoldProductResponseDTO> getTopSoldProducts();

    CommonResponseDTO<?> getTotalSoldProducts();

    CommonResponseDTO<?> getTotalProductRevenue();

    CommonResponseDTO<?> getTotalCustomers();

    CommonResponseDTO<?> getTotalLoggedIn();
}
