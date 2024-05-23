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
import shop.haui_megatech.domain.dto.global.*;
import shop.haui_megatech.domain.dto.order.*;
import shop.haui_megatech.domain.entity.Order;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.OrderMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.OrderRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.OrderService;
import shop.haui_megatech.utility.AuthenticationUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final MessageSourceUtil messageSourceUtil;
    private final OrderMapper       orderMapper;
    private final UserRepository    userRepository;
    private final OrderRepository   orderRepository;

    @Override
    public GlobalResponseDTO<?> getListOrderForUser(PaginationRequestDTO requestDTO) {
        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        if (requestDTO.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = requestDTO.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(requestDTO.fields())
                          .ascending()
                    : Sort.by(requestDTO.fields())
                          .descending();

        Pageable pageable =
                PageRequest.of(requestDTO.index(), requestDTO.limit(), sort);

        Page<Order> page = requestDTO.keyword() == null
                           ? orderRepository.findOrderByUserId(foundUser.get().getId(), pageable)
                           : orderRepository.searchOrderForUser(requestDTO.keyword(), foundUser.get().getId(), pageable);

        List<Order> orders = page.getContent();
        return GlobalResponseDTO
                .<List<OrderBaseDTO>>builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(PaginationDTO
                                .builder()
                                .keyword(requestDTO.keyword())
                                .pageIndex(requestDTO.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build()
                )
                .data(orders.stream()
                            .map(orderMapper::orderToOrderBase).toList()
                )
                .build();
    }

    @Override
    public GlobalResponseDTO<?> getListOrderForAdmin(PaginationRequestDTO requestDTO) {
        if (requestDTO.index() < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_PAGE_INDEX);

        Sort sort = requestDTO.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(requestDTO.fields())
                          .ascending()
                    : Sort.by(requestDTO.fields())
                          .descending();

        Pageable pageable = PageRequest.of(requestDTO.index(), requestDTO.limit(), sort);
        System.out.println(requestDTO.keyword());
        Page<Order> page = requestDTO.keyword() == null
                           ? orderRepository.findByAll(pageable)
                           : orderRepository.searchOrderForAdmin(requestDTO.keyword(), pageable);

        List<Order> orders = page.getContent();
        return GlobalResponseDTO
                .<List<OrderBaseDTO>>builder()
                .meta(MetaDTO
                        .builder()
                        .status(Status.SUCCESS)
                        .pagination(PaginationDTO
                                .builder()
                                .keyword(requestDTO.keyword())
                                .pageIndex(requestDTO.index())
                                .pageSize((short) page.getNumberOfElements())
                                .totalItems(page.getTotalElements())
                                .totalPages(page.getTotalPages())
                                .build())
                        .build()
                )
                .data(orders
                        .stream()
                        .map(orderMapper::orderToOrderBase).toList())
                .build();
    }

    @Override
    public GlobalResponseDTO<OrderItemResponseDTO> getOrderDetailForUser(Integer orderId) {
        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = orderRepository.findOrderDetailById_UserId(orderId, foundUser.get().getId()).get();
        return GlobalResponseDTO.<OrderItemResponseDTO>builder()
                                .meta(MetaDTO
                                        .builder()
                                        .status(Status.SUCCESS)
                                        .message("Get Order Detail For User")
                                        .build()
                                )
                                .data(orderMapper.orderItemResponseDto(order))
                                .build();
    }

    @Override
    public GlobalResponseDTO<OrderItemResponseDTO> getOrderDetailForAdmin(Integer orderId) {
        Order order = orderRepository.findById(orderId).get();

        return GlobalResponseDTO.<OrderItemResponseDTO>builder()
                                .meta(MetaDTO.builder()
                                             .status(Status.SUCCESS)
                                             .message("Get Order Detail For Admin")
                                             .build()
                                )
                                .data(orderMapper.orderItemResponseDto(order))
                                .build();
    }

    @Override
    public GlobalResponseDTO<OrderBaseDTO> addOrderForUser(AddOrderForUserRequestDTO requestDTO) {
        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        Order order = orderMapper.addOrderRequestForUserDTOtoOrder(requestDTO);
        order.setUser(foundUser.get());
        Order saveOrder = orderRepository.save(order);
        return GlobalResponseDTO.<OrderBaseDTO>builder()
                                .meta(MetaDTO.builder()
                                             .status(Status.SUCCESS)
                                             .message(messageSourceUtil.getMessage(SuccessMessage.Order.ADDED_ONE))
                                             .build())
                                .data(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public GlobalResponseDTO<OrderBaseDTO> addOrderForAdmin(AddOrderForAdminRequestDTO requestDTO) {
        Order order = orderMapper.addOrderRequestForAdminDTOtoOrder(requestDTO);
        Order saveOrder = orderRepository.save(order);
        return GlobalResponseDTO.<OrderBaseDTO>builder()
                                .meta(MetaDTO.builder()
                                             .status(Status.SUCCESS)
                                             .message(messageSourceUtil.getMessage(SuccessMessage.Order.ADDED_ONE))

                                             .build())
                                .data(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public GlobalResponseDTO<OrderBaseDTO> updateOrderForUser(ModifyOrderForUserRequestDTO requestDTO) {
        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = orderRepository.findOrderDetailById_UserId(requestDTO.orderId(), foundUser.get().getId()).get();
        if (order == null)
            throw new NotFoundException(ErrorMessage.Order.NOT_FOUND);

        orderRepository.deleteById(order.getId());

        Order orderUpdate = orderMapper.addOrderRequestForUserDTOtoOrder(requestDTO.addOrderForUserRequestDTO());

        orderUpdate.setId(order.getId());
        orderUpdate.setUser(foundUser.get());
        Order saveOrder = orderRepository.save(orderUpdate);
        return GlobalResponseDTO.<OrderBaseDTO>builder()
                                .meta(MetaDTO.builder()
                                             .status(Status.SUCCESS)
                                             .message(messageSourceUtil.getMessage(SuccessMessage.Order.UPDATED_ONE))
                                             .build())
                                .data(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public GlobalResponseDTO<OrderBaseDTO> updateOrderForAdmin(ModifyOrderForAdminRequestDTO requestDTO) {
        Optional<User> foundUser = userRepository.findById(requestDTO.addOrderForAdminRequestDTO().userId());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);
        Order order = orderRepository.findOrderDetailById_UserId(requestDTO.orderId(), foundUser.get().getId()).get();
        if (order == null)
            throw new NotFoundException(ErrorMessage.Order.NOT_FOUND);

        orderRepository.deleteById(order.getId());

        Order orderUpdate = orderMapper.addOrderRequestForAdminDTOtoOrder(requestDTO.addOrderForAdminRequestDTO());
        orderUpdate.setId(order.getId());
        orderUpdate.setUser(foundUser.get());
        Order saveOrder = orderRepository.save(orderUpdate);
        return GlobalResponseDTO.<OrderBaseDTO>builder()
                                .meta(MetaDTO.builder()
                                             .status(Status.SUCCESS)
                                             .message(messageSourceUtil.getMessage(SuccessMessage.Order.UPDATED_ONE))
                                             .build())
                                .data(orderMapper.orderToOrderBase(saveOrder))
                                .build();
    }

    @Override
    public GlobalResponseDTO<OrderBaseDTO> deleteOrderForAdmin(int orderId) {
        Order order = orderRepository.findById(orderId).get();
        if (order == null)
            throw new NotFoundException(ErrorMessage.Order.NOT_FOUND);
        orderRepository.deleteById(order.getId());
        return GlobalResponseDTO.<OrderBaseDTO>builder()
                                .meta(MetaDTO.builder()
                                             .status(Status.SUCCESS)
                                             .message("Delete Order")
                                             .build()
                                )
                                .data(orderMapper.orderToOrderBase(order))
                                .build();

    }
}
