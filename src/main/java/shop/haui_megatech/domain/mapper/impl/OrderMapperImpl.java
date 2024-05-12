package shop.haui_megatech.domain.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.domain.dto.order.AddOrderForAdminRequestDTO;
import shop.haui_megatech.domain.dto.order.AddOrderForUserRequestDTO;
import shop.haui_megatech.domain.dto.order.OrderBaseDTO;
import shop.haui_megatech.domain.dto.order.OrderItemResponseDTO;
import shop.haui_megatech.domain.dto.order_detail.OrderDetailRequestDTO;
import shop.haui_megatech.domain.entity.*;
import shop.haui_megatech.domain.mapper.OrderDetailMapper;
import shop.haui_megatech.domain.mapper.OrderMapper;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.utility.DecimalFormatUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OrderMapperImpl implements OrderMapper {
    private final DecimalFormatUtil decimalFormatUtil;
    private final UserRepository    userRepository;
    private final OrderDetailMapper orderDetailMapper;

    @Override
    public Order addOrderRequestForUserDTOtoOrder(AddOrderForUserRequestDTO requestDTO) {
        float shippingCost = 200000;
        if (requestDTO.orderWeight() < 5) shippingCost = 100000;

        Date payTime = null;
        if (requestDTO.paymentMethod() == PaymentMethod.ONLINE) payTime = new Date();

        Order order = Order.builder()
                           .shippingCost(shippingCost)
                           .subTotal(requestDTO.subTotal())
                           .tax(requestDTO.tax())
                           .total(requestDTO.subTotal() + requestDTO.tax())
                           .paymentMethod(requestDTO.paymentMethod())
                           .payTime(payTime)
                           .orderTime(new Date())
                           .deliverTime(0)
                           .orderWeight(requestDTO.orderWeight())
                           .address(requestDTO.address())
                           .status(OrderStatus.NEW)
                           .build();
        List<OrderDetail> list = new ArrayList<OrderDetail>();
        for (OrderDetailRequestDTO item : requestDTO.orderDetailRequestDTOList())
            list.add(orderDetailMapper.orderDetailRequestDTOtoOrderDetail(item, order));
        order.setOrderDetails(list);
        return order;
    }

    @Override
    public Order addOrderRequestForAdminDTOtoOrder(AddOrderForAdminRequestDTO requestDTO) {
        Optional<User> foundUser = userRepository.findById(requestDTO.userId());
        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = Order.builder()
                           .shippingCost(requestDTO.shippingCost())
                           .subTotal(requestDTO.subTotal())
                           .tax(requestDTO.tax())
                           .total(requestDTO.total())
                           .paymentMethod(requestDTO.paymentMethod())
                           .payTime(requestDTO.payTime())
                           .orderTime(requestDTO.orderTime())
                           .deliverTime(requestDTO.deliverTime())
                           .orderWeight(requestDTO.orderWeight())
                           .address(requestDTO.address())
                           .status(requestDTO.status())
                           .user(foundUser.get())
                           .build();
        List<OrderDetail> list = new ArrayList<OrderDetail>();
        for (OrderDetailRequestDTO item : requestDTO.orderDetailRequestDTOList())
            list.add(orderDetailMapper.orderDetailRequestDTOtoOrderDetail(item, order));
        order.setOrderDetails(list);
        return order;
    }

    @Override
    public OrderBaseDTO orderToOrderBase(Order order) {
        return OrderBaseDTO.builder()
                           .id(order.getId())
                           .firstName(order.getUser().getFirstName())
                           .lastName(order.getUser().getLastName())
                           .address(order.getAddress())
                           .paymentMethod(order.getPaymentMethod())
                           .orderStatus(order.getStatus())
                           .orderTime(order.getOrderTime())
                           .total(order.getTotal())
                           .build();
    }

    @Override
    public OrderItemResponseDTO orderItemResponseDto(Order order) {
        return OrderItemResponseDTO.builder()
                                   .orderId(order.getId())
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
