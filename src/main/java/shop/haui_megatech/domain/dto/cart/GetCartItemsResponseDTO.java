package shop.haui_megatech.domain.dto.cart;

import lombok.Builder;

import java.util.List;

@Builder
public record GetCartItemsResponseDTO(
        Integer totalItems,
        List<CartItemDTO> cartItems
) {
}
