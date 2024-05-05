package shop.haui_megatech.domain.dto.common;

import lombok.Builder;

import java.util.List;

@Builder
public record ListIdsRequestDTO(
        List<Integer> ids
) {
}
