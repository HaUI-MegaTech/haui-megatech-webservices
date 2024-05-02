package shop.haui_megatech.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.constant.SuccessMessageConstant;
import shop.haui_megatech.domain.dto.cart.GetCartItemsRequestDTO;
import shop.haui_megatech.domain.dto.cart.GetCartItemsResponseDTO;
import shop.haui_megatech.domain.dto.cart.ModifyCartItemRequestDTO;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.domain.dto.common.ListIdsRequestDTO;
import shop.haui_megatech.domain.dto.common.RequestIdDTO;
import shop.haui_megatech.domain.dto.user.UserDTO;
import shop.haui_megatech.domain.entity.CartItem;
import shop.haui_megatech.domain.entity.Product;
import shop.haui_megatech.domain.entity.User;
import shop.haui_megatech.domain.mapper.CartItemMapper;
import shop.haui_megatech.exception.InvalidRequestParamException;
import shop.haui_megatech.exception.NotFoundException;
import shop.haui_megatech.repository.CartRepository;
import shop.haui_megatech.repository.ProductRepository;
import shop.haui_megatech.repository.UserRepository;
import shop.haui_megatech.service.CartService;
import shop.haui_megatech.utility.JwtTokenUtil;
import shop.haui_megatech.utility.MessageSourceUtil;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository    cartRepository;
    private final UserRepository    userRepository;
    private final MessageSourceUtil messageSourceUtil;
    private final JwtTokenUtil      jwtTokenUtil;
    private final ProductRepository productRepository;

    @Override
    public CommonResponseDTO<?> addOne(ModifyCartItemRequestDTO request) {
        if (request.quantity() <= 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_CART_ITEM_QUANTITY);

        String username = jwtTokenUtil.extractUsername(request.token());

        Optional<User> foundUser = userRepository.findActiveUserByUsername(username);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        Optional<Product> foundProduct = productRepository.findById(request.productId());

        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        cartRepository.save(CartItem.builder()
                                    .user(foundUser.get())
                                    .product(foundProduct.get())
                                    .quantity(request.quantity())
                                    .build());

        return CommonResponseDTO.<UserDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Cart.ADDED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> updateOne(Integer id, ModifyCartItemRequestDTO request) {
        if (request.quantity() <= 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_CART_ITEM_QUANTITY);

        Optional<CartItem> foundCartItem = cartRepository.findById(id);
        if (foundCartItem.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Cart.NOT_FOUND);

        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(jwtTokenUtil.extractUsername(request.token()));
        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        if (!Objects.equals(foundCartItem.get().getUser().getId(), foundUser.get().getId()))
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        Optional<Product> foundProduct = productRepository.findById(request.productId());
        if (foundProduct.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.Product.NOT_FOUND);

        foundCartItem.get().setQuantity(request.quantity());
        cartRepository.save(foundCartItem.get());

        return CommonResponseDTO.<UserDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Cart.UPDATED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteOne(RequestIdDTO request) {
        if (request.id() < 0)
            throw new InvalidRequestParamException(ErrorMessageConstant.Request.NEGATIVE_CART_ITEM_ID);

        Optional<User> foundUser =
                userRepository.findActiveUserByUsername(jwtTokenUtil.extractUsername(request.token()));
        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        cartRepository.deleteById(request.id());

        return CommonResponseDTO.<UserDTO>builder()
                                .success(true)
                                .message(messageSourceUtil.getMessage(SuccessMessageConstant.Cart.HARD_DELETED_ONE))
                                .build();
    }

    @Override
    public CommonResponseDTO<?> hardDeleteList(ListIdsRequestDTO request) {
        return null;
    }


    @Override
    public GetCartItemsResponseDTO getCartItems(GetCartItemsRequestDTO request) {
        String username = jwtTokenUtil.extractUsername(request.token());
        Optional<User> foundUser = userRepository.findActiveUserByUsername(username);

        if (foundUser.isEmpty())
            throw new NotFoundException(ErrorMessageConstant.User.NOT_FOUND);

        return GetCartItemsResponseDTO.builder()
                                      .totalItems(foundUser.get().getCartItems().size())
                                      .cartItems(foundUser.get()
                                                          .getCartItems()
                                                          .parallelStream()
                                                          .map(CartItemMapper.INSTANCE::toCartItemDTO)
                                                          .toList())
                                      .build();
    }
}
