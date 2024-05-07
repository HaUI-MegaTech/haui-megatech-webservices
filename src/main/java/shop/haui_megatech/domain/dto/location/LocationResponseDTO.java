package shop.haui_megatech.domain.dto.location;

import lombok.Builder;

import java.util.List;

@Builder
public record LocationResponseDTO<T>(
        String message,
        List<T> items
) {
}
