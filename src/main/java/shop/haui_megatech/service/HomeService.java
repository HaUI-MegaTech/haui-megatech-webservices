package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.order.LatestOrderResponseDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.product.TopSoldProductResponseDTO;

import java.util.List;

public interface HomeService {
    GlobalResponseDTO<List<ProductCountByBrandResponseDTO>> getProductCountByBrand();

    GlobalResponseDTO<List<TopSoldProductResponseDTO>> getTopSoldProducts();

    GlobalResponseDTO<?> getTotalSoldProducts();

    GlobalResponseDTO<?> getTotalProductRevenue();

    GlobalResponseDTO<?> getTotalCustomers();

    GlobalResponseDTO<?> getTotalLoggedIn();

    GlobalResponseDTO<List<LatestOrderResponseDTO>> getLatestOrder();
}
