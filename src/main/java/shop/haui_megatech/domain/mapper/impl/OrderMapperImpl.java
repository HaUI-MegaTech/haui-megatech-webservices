package shop.haui_megatech.domain.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.domain.dto.order.AddOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.AddOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.order.OrderBaseDTO;
import shop.haui_megatech.domain.dto.order.OrderItemResponseDTO;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.OrderDetailMapper;
import shop.haui_megatech.domain.mapper.OrderMapper;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.utility.DecimalFormatUtil;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    private final DecimalFormatUtil decimalFormatUtil;
    private final UserRepository userRepository;
    private final OrderDetailMapper orderDetailMapper;
    @Override
    public Order addOrderRequestForUserDTOtoOrder(AddOrderForUserRequestDTO requestDTO) {
        return Order.builder()
                .shippingCost(requestDTO.shippingCost())
                .subTotal(requestDTO.subTotal())
                .tax(requestDTO.tax())
                .total(requestDTO.total())
                .paymentMethod(requestDTO.paymentMethod())
                .payTime(new Date())
                .orderTime(new Date())
                .deliverTime(new Date())
                .orderWeight(requestDTO.orderWeight())
                .address(requestDTO.address())
                .status(requestDTO.status())
                .orderDetails(requestDTO.orderDetailRequestDTOList()
                        .stream()
                        .map(orderDetailMapper::orderDetailRequestDTOtoOrderDetail).toList())
                .build();
    }
    @Override
    public Order addOrderRequestForAdminDTOtoOrder(AddOrderForAdminRequestDTO requestDTO) {
        Optional<User> foundUser = userRepository.findById(requestDTO.userId());
        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);
        return Order.builder()
                .shippingCost(requestDTO.shippingCost())
                .subTotal(requestDTO.subTotal())
                .tax(requestDTO.tax())
                .total(requestDTO.total())
                .paymentMethod(requestDTO.paymentMethod())
                .payTime(new Date())
                .orderTime(new Date())
                .deliverTime(new Date())
                .orderWeight(requestDTO.orderWeight())
                .address(requestDTO.address())
                .status(requestDTO.status())
                .orderDetails(requestDTO.orderDetailRequestDTOList()
                        .stream()
                        .map(orderDetailMapper::orderDetailRequestDTOtoOrderDetail).toList())
                .user(foundUser.get())
                .build();
    }
    @Override
    public OrderBaseDTO orderToOrderBase (Order order) {
        return OrderBaseDTO.builder()
                .id(order.getId())
                .firstName(order.getUser().getFirstName())
                .lastName(order.getUser().getLastName())
                .address(order.getAddress())
                .paymentMethod(order.getPaymentMethod())
                .orderStatus(order.getStatus())
                .total(order.getTotal())
                .build();
    }

    @Override
    public OrderItemResponseDTO orderItemResponseDto(Order order) {
        return OrderItemResponseDTO.builder()
                .shippingCost(decimalFormatUtil.format(order.getShippingCost()))
                .subTotal(decimalFormatUtil.format(order.getSubTotal()))
                .tax(decimalFormatUtil.format(order.getTax()))
                .total(decimalFormatUtil.format(order.getTotal()))
                .paymentMethod(order.getPaymentMethod())
                .payTime(order.getPayTime())
                .orderTime(order.getOrderTime())
                .deliverTime(order.getDeliverTime())
                .orderWeight(order.getOrderWeight())
                .address(order.getAddress())
                .status(order.getStatus())
                .orderOrderDetailResponseDTO(order.getOrderDetails()
                        .stream()
                        .map(orderDetailMapper::orderDetailToOrderOrderDetailResponseDto).toList())
                .build();
    }
}
