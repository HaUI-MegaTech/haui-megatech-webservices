package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.BriefProductResponseDTO;
import shop.haui_megatech.domain.dto.product.TopSoldProductResponseDTO;
import shop.haui_megatech.domain.entity.Brand;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.repository.BrandRepository;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.service.HomeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final BrandRepository   brandRepository;
    private final ProductRepository productRepository;

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

    @Override
    public NoPaginationResponseDTO<TopSoldProductResponseDTO> getTopSoldProducts() {
        Sort sort = Sort.by("totalSold").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> list = page.getContent();
        return NoPaginationResponseDTO
                .<TopSoldProductResponseDTO>builder()
                .success(true)
                .items(list.parallelStream()
                           .map(item ->
                                   TopSoldProductResponseDTO
                                           .builder()
                                           .id(item.getId())
                                           .name(item.getName())
                                           .newPrice(item.getCurrentPrice())
                                           .totalSold(item.getTotalSold())
                                           .mainImageUrl(item.getMainImageUrl())
                                           .revenue((long) (item.getTotalSold() * (item.getCurrentPrice() - item.getImportPrice())))
                                           .build()
                           )
                           .toList()
                )
                .build();
    }
}
