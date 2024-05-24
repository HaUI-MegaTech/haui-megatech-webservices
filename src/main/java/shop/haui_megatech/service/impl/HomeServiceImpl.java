package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.domain.dto.global.GlobalResponseDTO;
import shop.haui_megatech.domain.dto.global.NoPaginatedMeta;
import shop.haui_megatech.domain.dto.global.Status;
import shop.haui_megatech.domain.dto.home.ProductCountByBrandResponseDTO;
import shop.haui_megatech.domain.dto.order.LatestOrderResponseDTO;
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
    public GlobalResponseDTO<NoPaginatedMeta, List<ProductCountByBrandResponseDTO>> getProductCountByBrand() {
        List<Brand> brands = brandRepository.findAll();

        return GlobalResponseDTO
                .<NoPaginatedMeta, List<ProductCountByBrandResponseDTO>>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .build())
                .data(brands.parallelStream()
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
    public GlobalResponseDTO<NoPaginatedMeta, List<TopSoldProductResponseDTO>> getTopSoldProducts() {
        Sort sort = Sort.by("totalSold").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> list = page.getContent();
        return GlobalResponseDTO
                .<NoPaginatedMeta, List<TopSoldProductResponseDTO>>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .build())
                .data(list.parallelStream()
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
    public GlobalResponseDTO<NoPaginatedMeta, Integer> getTotalSoldProducts() {
        List<Product> list = productRepository.findAll();
        Integer total = list.stream().mapToInt(Product::getTotalSold).sum();

        return GlobalResponseDTO
                .<NoPaginatedMeta, Integer>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(total)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Double> getTotalProductRevenue() {
        List<Product> list = productRepository.findAll();
        Double totalRevenue = list.stream()
                                  .mapToDouble(item ->
                                          item.getTotalSold() * (item.getCurrentPrice() - item.getImportPrice())
                                  )
                                  .sum();

        return GlobalResponseDTO
                .<NoPaginatedMeta, Double>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(totalRevenue)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Integer> getTotalCustomers() {
        List<User> list = userRepository.findAll();
        list.removeIf(item -> !item.getRole().equals(Role.CUSTOMER));

        return GlobalResponseDTO
                .<NoPaginatedMeta, Integer>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(list.size())
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, Integer> getTotalLoggedIn() {
        List<LoginStatistic> list = loginStatisticRepository.findAll();
        Integer total = list.stream().mapToInt(LoginStatistic::getLoggedIn).sum();

        return GlobalResponseDTO
                .<NoPaginatedMeta, Integer>builder()
                .meta(NoPaginatedMeta.builder().status(Status.SUCCESS).build())
                .data(total)
                .build();
    }

    @Override
    public GlobalResponseDTO<NoPaginatedMeta, List<LatestOrderResponseDTO>> getLatestOrder() {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Order> page = orderRepository.findAll(pageable);
        List<Order> list = page.getContent();

        return GlobalResponseDTO
                .<NoPaginatedMeta, List<LatestOrderResponseDTO>>builder()
                .meta(NoPaginatedMeta
                        .builder()
                        .status(Status.SUCCESS)
                        .build()
                )
                .data(list.stream()
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
