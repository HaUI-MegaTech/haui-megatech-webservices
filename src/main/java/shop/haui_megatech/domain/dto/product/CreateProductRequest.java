package shop.haui_megatech.domain.dto.product;

import org.springframework.web.bind.annotation.RequestParam;

public record CreateProductRequest(
        String name,
        Float price
) {
}
