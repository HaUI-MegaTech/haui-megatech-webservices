package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.entity.Brand;
import shop.haui_megatech.repository.BrandRepository;
import shop.haui_megatech.service.HomeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final BrandRepository brandRepository;

    @Override
    public NoPaginationResponseDTO<ProductCountByBrandResponseDTO> getProductCountByBrand() {
        List<Brand> brands = brandRepository.findAll();

        return NoPaginationResponseDTO
                .<ProductCountByBrandResponseDTO>builder()
                .success(true)
                .items(brands.parallelStream()
                             .map(item ->
                                     ProductCountByBrandResponseDTO
                                             .builder()
                                             .name(item.getName())
                                             .count(item.getProducts().size())
                                             .build()
                             )
                             .toList()
                )
                .build();
    }
}
