package shop.haui_megatech.domain.mapper;

import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.dto.order_detail.OrderDetailResponseDTO;
import shop.haui_megatech.domain.dto.order_detail.OrderOrderDetailResponseDTO;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.domain.entity.OrderDetail;

public interface OrderDetailMapper {
    OrderDetail orderDetailRequestDTOtoOrderDetail(OrderDetailRequestDTO requestDTO, Order order);

    OrderDetailResponseDTO orderDetailToOrderDetailResponseDTO(OrderDetail orderDetail);

    OrderOrderDetailResponseDTO orderDetailToOrderOrderDetailResponseDto(OrderDetail orderDetail);
}
