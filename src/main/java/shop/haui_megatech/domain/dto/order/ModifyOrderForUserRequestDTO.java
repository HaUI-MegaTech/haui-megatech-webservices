package shop.haui_megatech.domain.dto.order;

public record ModifyOrderForUserRequestDTO(
        int orderId,
        AddOrderForUserRequestDTO addOrderForUserRequestDTO
) {
}
