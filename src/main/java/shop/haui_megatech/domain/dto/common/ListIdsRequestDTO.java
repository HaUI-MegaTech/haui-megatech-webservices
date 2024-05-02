package shop.haui_megatech.domain.dto.common;

import java.util.List;

public record ListIdsRequestDTO(
        String token,
        List<Integer> ids
) {
}
