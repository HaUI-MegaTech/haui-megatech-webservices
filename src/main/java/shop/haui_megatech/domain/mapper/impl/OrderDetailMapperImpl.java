package shop.haui_megatech.domain.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.dto.order_detail.OrderDetailResponseDTO;
import shop.haui_megatech.domain.dto.order_detail.OrderOrderDetailResponseDTO;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.domain.entity.OrderDetail;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.mapper.OrderDetailMapper;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.utility.DecimalFormatUtil;

@Component
@RequiredArgsConstructor
public class OrderDetailMapperImpl implements OrderDetailMapper {
    private final DecimalFormatUtil decimalFormatUtil;
    private final ProductRepository productRepository;

    @Override
    public OrderDetail orderDetailRequestDTOtoOrderDetail(OrderDetailRequestDTO requestDTO, Order order) {
        int product_id = requestDTO.productId();
        Product foundProduct = productRepository.findById(product_id).get();
        return OrderDetail.builder()
                          .quantity(requestDTO.quantity())
                          .product(foundProduct)
                          .price(foundProduct.getCurrentPrice())
                          .order(order)
                          .build();
    }

    @Override
    public OrderDetailResponseDTO orderDetailToOrderDetailResponseDTO(OrderDetail orderDetail) {
        return OrderDetailResponseDTO.builder()
                                     .quantity(orderDetail.getQuantity())
                                     .price(orderDetail.getPrice())
                                     .product(orderDetail.getProduct())
                                     .build();
    }

    @Override
    public OrderOrderDetailResponseDTO orderDetailToOrderOrderDetailResponseDto(OrderDetail orderDetail) {
        return OrderOrderDetailResponseDTO.builder()
                                          .quatity(orderDetail.getQuantity())
                                          .price(decimalFormatUtil.format(orderDetail.getPrice()))
                                          .proName(orderDetail.getProduct().getName())
                                          .proId(orderDetail.getProduct().getId())
                                          .build();
    }

}
