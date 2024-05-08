package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessage;
import shop.haui_megatech.constant.PaginationConstant;
import shop.haui_megatech.constant.SuccessMessage;
import shop.haui_megatech.domain.dto.PaginationDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.OrderMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.OrderRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.OrderService;
import shop.haui_megatech.utility.JwtTokenUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final MessageSourceUtil messageSourceUtil;
    private final JwtTokenUtil      jwtTokenUtil;
    private final OrderMapper       orderMapper;
    private final UserRepository    userRepository;
    private final OrderRepository   orderRepository;

    @Override
    public PaginationDTO.Response<?> getListOrderForUser(ListOrdersForUserRequestDTO requestDTO) {
        String username = jwtTokenUtil.extractUsername(requestDTO.token());

        Optional<User> foundUser = userRepository.findActiveUserByUsername(username);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        if (requestDTO.paginationRequestDTO().index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = requestDTO.paginationRequestDTO().direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(requestDTO.paginationRequestDTO().fields())
                          .ascending()
                    : Sort.by(requestDTO.paginationRequestDTO().fields())
                          .descending();

        Pageable pageable =
                PageRequest.of(requestDTO.paginationRequestDTO().index(), requestDTO.paginationRequestDTO().limit(), sort);

        Page<Order> page = requestDTO.paginationRequestDTO().keyword() == null
                           ? orderRepository.findOrderByUserId(foundUser.get().getId(), pageable)
                           : orderRepository.searchOrderForUser(requestDTO.paginationRequestDTO().keyword(), foundUser.get().getId(), pageable);

        List<Order> orders = page.getContent();
        return PaginationDTO.Response.<OrderBaseDTO>builder()
                                     .keyword(requestDTO.paginationRequestDTO().keyword())
                                     .pageIndex(requestDTO.paginationRequestDTO().index())
                                     .pageSize((short) page.getNumberOfElements())
                                     .totalItems(page.getTotalElements())
                                     .totalPages(page.getTotalPages())
                                     .items(orders
                                                    .stream()
                                                    .map(orderMapper::orderToOrderBase).toList())
                                     .build();
    }

    @Override
    public PaginationDTO.Response<?> getListOrderForAdmin(PaginationDTO.Request requestDTO) {
        if (requestDTO.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = requestDTO.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(requestDTO.fields())
                          .ascending()
                    : Sort.by(requestDTO.fields())
                          .descending();

        Pageable pageable = PageRequest.of(requestDTO.index(), requestDTO.limit(), sort);

        Page<Order> page = requestDTO.keyword() == null
                           ? orderRepository.findByAll(pageable)
                           : orderRepository.searchOrderForAdmin(requestDTO.keyword(), pageable);

        List<Order> orders = page.getContent();
        return PaginationDTO.Response.<OrderBaseDTO>builder()
                                     .keyword(requestDTO.keyword())
                                     .pageIndex(requestDTO.index())
                                     .pageSize((short) page.getNumberOfElements())
                                     .totalItems(page.getTotalElements())
                                     .totalPages(page.getTotalPages())
                                     .items(orders
                                                    .stream()
                                                    .map(orderMapper::orderToOrderBase).toList())
                                     .build();
    }

    @Override
    public CommonResponseDTO<OrderItemResponseDTO> getOrderDetailForUser(OrderItemForUserRequestDTO requestDTO) {
        String username = jwtTokenUtil.extractUsername(requestDTO.token());

        Optional<User> foundUser = userRepository.findActiveUserByUsername(username);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = orderRepository.findOrderDetailById_UserId(requestDTO.orderId(), foundUser.get().getId()).get();
        return CommonResponseDTO.<OrderItemResponseDTO>builder()
                                .success(true)
                                .message("Get Order Detail For User")
                                .item(orderMapper.orderItemResponseDto(order))
                                .build();
    }

    @Override
    public CommonResponseDTO<OrderItemResponseDTO> getOrderDetailForAdmin(Integer orderId) {
        Order order = orderRepository.findById(orderId).get();

        return CommonResponseDTO.<OrderItemResponseDTO>builder()
                                .success(true)
                                .message("Get Order Detail For Admin")
                                .item(orderMapper.orderItemResponseDto(order))
                                .build();
    }

    @Override
    public CommonResponseDTO<OrderBaseDTO> addOrderForUser(AddOrderForUserRequestDTO requestDTO) {
        String username = jwtTokenUtil.extractUsername(requestDTO.token());

        Optional<User> foundUser = userRepository.findActiveUserByUsername(username);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        Order order = orderMapper.addOrderRequestForUserDTOtoOrder(requestDTO);
        order.setUser(foundUser.get());
        Order saveOrder = orderRepository.save(order);
        return CommonResponseDTO.<OrderBaseDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.Order.ADDED_ONE))
                                .item(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public CommonResponseDTO<OrderBaseDTO> addOrderForAdmin(AddOrderForAdminRequestDTO requestDTO) {
        Order order = orderMapper.addOrderRequestForAdminDTOtoOrder(requestDTO);
        Order saveOrder = orderRepository.save(order);
        return CommonResponseDTO.<OrderBaseDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.Order.ADDED_ONE))
                                .item(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public CommonResponseDTO<OrderBaseDTO> updateOrderForUser(ModifyOrderForUserRequestDTO requestDTO) {
        String username = jwtTokenUtil.extractUsername(requestDTO.addOrderForUserRequestDTO().token());

        Optional<User> foundUser = userRepository.findActiveUserByUsername(username);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = orderRepository.findOrderDetailById_UserId(requestDTO.orderId(), foundUser.get().getId()).get();
        if (order == null)
            throw new NotFoundException(ErrorMessage.Order.NOT_FOUND);

        Order orderUpdate = orderMapper.addOrderRequestForUserDTOtoOrder(requestDTO.addOrderForUserRequestDTO());
        orderUpdate.setId(order.getId());
        orderUpdate.setUser(foundUser.get());
        Order saveOrder = orderRepository.save(orderUpdate);
        return CommonResponseDTO.<OrderBaseDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.Order.UPDATED_ONE))
                                .item(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public CommonResponseDTO<OrderBaseDTO> updateOrderForAdmin(ModifyOrderForAdminRequestDTO requestDTO) {
        Optional<User> foundUser = userRepository.findById(requestDTO.addOrderForAdminRequestDTO().userId());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = orderRepository.findOrderDetailById_UserId(requestDTO.orderId(), foundUser.get().getId()).get();
        if (order == null)
            throw new NotFoundException(ErrorMessage.Order.NOT_FOUND);

        Order orderUpdate = orderMapper.addOrderRequestForAdminDTOtoOrder(requestDTO.addOrderForAdminRequestDTO());
        orderUpdate.setId(order.getId());
        orderUpdate.setUser(foundUser.get());
        Order saveOrder = orderRepository.save(orderUpdate);
        return CommonResponseDTO.<OrderBaseDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessage.Order.UPDATED_ONE))
                                .item(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public CommonResponseDTO<OrderBaseDTO> deleteOrderForAdmin(int orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order == null)
            throw new NotFoundException(ErrorMessage.Order.NOT_FOUND);
        orderRepository.deleteById(order.getId());
        return CommonResponseDTO.<OrderBaseDTO>builder()
                                .success(true)
                                .message("Delete Order")
                                .item(orderMapper.orderToOrderBase(order))
                                .build();

    }
}
