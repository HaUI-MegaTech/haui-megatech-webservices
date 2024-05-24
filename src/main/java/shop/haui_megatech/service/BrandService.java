package shop.haui_megatech.service;

import shop.haui_megatech.domain.dto.brand.BrandResponseDTO;
import shop.haui_megatech.domain.dto.brand.BrandStatisticResponseDTO;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.global.PaginatedMeta;
import shop.haui_megatech.domain.dto.global.PaginationRequestDTO;

import java.util.List;

public interface BrandService {
    GlobalResponseDTO<NoPaginatedMeta, BrandResponseDTO> getOne(Integer id);

    GlobalResponseDTO<PaginatedMeta, List<BrandResponseDTO>> getList(PaginationRequestDTO request);

    GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalRevenue();

    GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalSold();

    GlobalResponseDTO<NoPaginatedMeta, List<BrandStatisticResponseDTO>> getTotalView();
}
