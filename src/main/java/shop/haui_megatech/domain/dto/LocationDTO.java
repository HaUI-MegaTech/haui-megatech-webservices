package shop.haui_megatech.domain.dto;

import lombok.Builder;

import java.util.List;

public record LocationDTO() {

    @Builder
    public record Response<T>(
            String message,
            List<T> items
    ) {}
}
