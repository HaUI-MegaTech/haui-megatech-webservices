package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.order.LatestOrderResponseDTO;
import shop.haui_megatech.domain.dto.product.TopSoldProductResponseDTO;

import java.util.List;

public interface HomeService {
    GlobalResponseDTO<NoPaginatedMeta, List<ProductCountByBrandResponseDTO>> getProductCountByBrand();

    GlobalResponseDTO<NoPaginatedMeta, List<TopSoldProductResponseDTO>> getTopSoldProducts();

    GlobalResponseDTO<NoPaginatedMeta, Integer> getTotalSoldProducts();

    GlobalResponseDTO<NoPaginatedMeta, Double> getTotalProductRevenue();

    GlobalResponseDTO<NoPaginatedMeta, Integer> getTotalCustomers();

    GlobalResponseDTO<NoPaginatedMeta, Integer> getTotalLoggedIn();

    GlobalResponseDTO<NoPaginatedMeta, List<LatestOrderResponseDTO>> getLatestOrder();
}
