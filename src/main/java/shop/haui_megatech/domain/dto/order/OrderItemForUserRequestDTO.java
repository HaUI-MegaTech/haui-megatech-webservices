package shop.haui_megatech.domain.dto.order;

public record OrderItemForUserRequestDTO(
        String token,
        int orderId
) {
}