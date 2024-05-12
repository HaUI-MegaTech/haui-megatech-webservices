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
import shop.haui_megatech.domain.dto.cart.BriefCartItemResponseDTO;
import shop.haui_megatech.domain.dto.cart.CartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationRequestDTO;
import shop.haui_megatech.domain.dto.pagination.PaginationResponseDTO;
import shop.haui_megatech.domain.dto.user.FullUserResponseDTO;
import shop.haui_megatech.domain.entity.CartItem;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.CartItemMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.CartItemRepository;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.CartItemService;
import shop.haui_megatech.utility.AuthenticationUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final UserRepository     userRepository;
    private final MessageSourceUtil  messageSourceUtil;
    private final ProductRepository  productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CommonResponseDTO<?> addOne(CartItemRequestDTO request) {
        if (request.quantity() <= 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_CART_ITEM_QUANTITY);

        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        Optional<Product> foundProduct = productRepository.findById(request.productId());

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        Optional<CartItem> existedCartItem = foundUser.get().getCartItems()
                                                      .parallelStream()
                                                      .filter(item -> item.getProduct().getId().equals(request.productId()))
                                                      .findFirst();

        if (existedCartItem.isPresent()) {
            existedCartItem.get().setQuantity(existedCartItem.get().getQuantity() + request.quantity());
            cartItemRepository.save(existedCartItem.get());
            return CommonResponseDTO
                    .<FullUserResponseDTO>builder()
                    .success(true)
                    .message(messageSourceUtil.getMessage(SuccessMessage.Cart.ADDED_ONE))
                    .build();
        }


        cartItemRepository.save(CartItem.builder()
                                        .product(foundProduct.get())
                                        .user(foundUser.get())
                                        .quantity(request.quantity())
                                        .build());

        return CommonResponseDTO
                .builder()
                .success(true)
                .message(messageSourceUtil.getMessage(SuccessMessage.Cart.ADDED_ONE))
                .build();
    }

    @Override
    public CommonResponseDTO<?> updateOne(Integer id, CartItemRequestDTO request) {
        if (request.quantity() <= 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_CART_ITEM_QUANTITY);

        Optional<CartItem> foundCartItem = cartItemRepository.findById(id);
        if (foundCartItem.isEmpty())
            throw new NotFoundException(ErrorMessage.Cart.NOT_FOUND);

        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        if (!Objects.equals(foundCartItem.get().getUser().getId(), foundUser.get().getId()))
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        Optional<Product> foundProduct = productRepository.findById(request.productId());
        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessage.Product.NOT_FOUND);

        foundCartItem.get().setQuantity(request.quantity());
        cartItemRepository.save(foundCartItem.get());

        return CommonResponseDTO.
                builder()
                .success(true)
                .message(messageSourceUtil.getMessage(SuccessMessage.Cart.UPDATED_ONE))
                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteOne(Integer cartItemId) {
        if (cartItemId < 0)
            throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_CART_ITEM_ID);

        Optional<User> foundUser = userRepository.findActiveUserByUsername(
                AuthenticationUtil.getRequestedUser().getUsername()
        );

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        cartItemRepository.deleteById(cartItemId);

        return CommonResponseDTO.
                builder()
                .success(true)
                .message(messageSourceUtil.getMessage(SuccessMessage.Cart.HARD_DELETED_ONE))
                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        if (request.ids().size() == 1) return this.hardDeleteOne(request.ids().getFirst());

        request.ids().parallelStream().forEach(item -> {
            if (item < 0)
                throw new InvalidRequestParamException(ErrorMessage.Request.NEGATIVE_CART_ITEM_ID);
        });

        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        ArrayList<Integer> checkedCartItemIds = new ArrayList<>(
                foundUser.get()
                         .getCartItems()
                         .parallelStream()
                         .map(CartItem::getId)
                         .filter(request.ids()::contains)
                         .toList()
        );

        cartItemRepository.deleteAllByIds(checkedCartItemIds);

        return CommonResponseDTO.
                builder()
                .success(true)
                .message(
                        messageSourceUtil.getMessage(
                                SuccessMessage.Cart.HARD_DELETED_LIST,
                                checkedCartItemIds.size()
                        )
                )
                .build();
    }


    @Override
    public PaginationResponseDTO<BriefCartItemResponseDTO> getListByUser(Integer userId, PaginationRequestDTO request) {
        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(AuthenticationUtil.getRequestedUser().getUsername());

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        if (!Objects.equals(foundUser.get().getId(), userId))
            throw new NotFoundException(ErrorMessage.User.NOT_FOUND);

        Sort sort = request.direction().equals(PaginationConstant.DEFAULT_ORDER)
                    ? Sort.by(request.fields())
                          .ascending()
                    : Sort.by(request.fields())
                          .descending();

        Pageable pageable = PageRequest.of(request.index(), request.limit(), sort);

        if (request.keyword() != null) {
            String[] keywords = request.keyword().split(" ");
            List<CartItem> cartItems = new ArrayList<>();
            int pageCount = 0;
            for (String keyword : keywords) {
                ++pageCount;
                Page<CartItem> page = cartItemRepository.searchCartItemsByUserIdAndKeyword(
                        keyword,
                        foundUser.get().getId(),
                        pageable
                );
                cartItems.addAll(page.getContent());
            }
            return PaginationResponseDTO
                    .<BriefCartItemResponseDTO>builder()
                    .keyword(request.keyword())
                    .pageIndex(request.index())
                    .pageSize(request.limit())
                    .totalItems((long) cartItems.size())
                    .totalPages(pageCount)
                    .items(cartItems
                            .parallelStream()
                            .map(CartItemMapper.INSTANCE::toBriefCartItemResponseDTO)
                            .collect(Collectors.toList())
                    )
                    .build();
        }

        Page<CartItem> page = cartItemRepository.getCartItemsByUserId(foundUser.get().getId(), pageable);

        List<CartItem> cartItems = page.getContent();

        return PaginationResponseDTO
                .<BriefCartItemResponseDTO>builder()
                .pageIndex(request.index())
                .pageSize((short) page.getNumberOfElements())
                .totalItems(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .items(cartItems
                        .parallelStream()
                        .map(CartItemMapper.INSTANCE::toBriefCartItemResponseDTO)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
