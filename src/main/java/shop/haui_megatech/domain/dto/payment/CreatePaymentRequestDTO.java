package shop.haui_megatech.domain.dto.payment;

import org.springframework.web.bind.annotation.RequestParam;

public record CreatePaymentRequestDTO(
        @RequestParam String ids,

        @RequestParam Integer addressId
) {}
