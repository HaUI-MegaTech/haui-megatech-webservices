package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.order.LatestOrderResponseDTO;
import shop.haui_megatech.domain.dto.pagination.NoPaginationResponseDTO;
import shop.haui_megatech.domain.dto.product.BriefProductResponseDTO;
import shop.haui_megatech.domain.dto.product.TopSoldProductResponseDTO;
import shop.haui_megatech.domain.entity.*;
import shop.haui_megatech.domain.entity.enums.Role;
import shop.haui_megatech.repository.*;
import shop.haui_megatech.service.HomeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final BrandRepository          brandRepository;
    private final ProductRepository        productRepository;
    private final UserRepository           userRepository;
    private final LoginStatisticRepository loginStatisticRepository;
    private final OrderRepository          orderRepository;

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
                                           .revenue((double) (item.getTotalSold() * (item.getCurrentPrice() - item.getImportPrice())))
                                           .build()
                           )
                           .toList()
                )
                .build();
    }

    @Override
    public CommonResponseDTO<?> getTotalSoldProducts() {
        List<Product> list = productRepository.findAll();
        Integer total = list.stream().mapToInt(Product::getTotalSold).sum();

        return CommonResponseDTO
                .builder()
                .success(true)
                .item(total)
                .build();
    }

    @Override
    public CommonResponseDTO<?> getTotalProductRevenue() {
        List<Product> list = productRepository.findAll();
        Double totalRevenue = list.stream()
                                  .mapToDouble(item ->
                                          item.getTotalSold() * (item.getCurrentPrice() - item.getImportPrice())
                                  )
                                  .sum();

        return CommonResponseDTO
                .builder()
                .success(true)
                .item(totalRevenue)
                .build();
    }

    @Override
    public CommonResponseDTO<?> getTotalCustomers() {
        List<User> list = userRepository.findAll();
        list.removeIf(item -> !item.getRole().equals(Role.CUSTOMER));

        return CommonResponseDTO
                .builder()
                .success(true)
                .item(list.size())
                .build();
    }

    @Override
    public CommonResponseDTO<?> getTotalLoggedIn() {
        List<LoginStatistic> list = loginStatisticRepository.findAll();
        Integer total = list.stream().mapToInt(LoginStatistic::getLoggedIn).sum();

        return CommonResponseDTO
                .builder()
                .success(true)
                .item(total)
                .build();
    }

    @Override
    public NoPaginationResponseDTO<LatestOrderResponseDTO> getLatestOrder() {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> list = page.getContent();

        return NoPaginationResponseDTO
                .<LatestOrderResponseDTO>builder()
                .success(true)
                .items(list.stream()
                           .map(item ->
                                   LatestOrderResponseDTO
                                           .builder()
                                           .id(item.getId())
                                           .customer(String.format(
                                                   "%s %s (%s)",
                                                   item.getUser().getFirstName(),
                                                   item.getUser().getLastName(),
                                                   item.getUser().getUsername()
                                           ))
                                           .orderTime(item.getOrderTime())
                                           .total((double) item.getTotal())
                                           .status(item.getStatus())
                                           .build()
                           )
                           .toList()
                )
                .build();
    }


}
