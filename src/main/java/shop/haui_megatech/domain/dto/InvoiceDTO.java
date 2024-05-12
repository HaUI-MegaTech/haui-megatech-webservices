package shop.haui_megatech.domain.dto;

import java.util.List;

public record InvoiceDTO() {

    public record AddRequest(
            
            List<Integer> ids
    ) {}
}
