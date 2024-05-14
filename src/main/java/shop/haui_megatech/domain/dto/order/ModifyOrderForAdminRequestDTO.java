package shop.haui_megatech.domain.dto.order;

public record ModifyOrderForAdminRequestDTO(
        int orderId,
        AddOrderForAdminRequestDTO addOrderForAdminRequestDTO
) {
}
