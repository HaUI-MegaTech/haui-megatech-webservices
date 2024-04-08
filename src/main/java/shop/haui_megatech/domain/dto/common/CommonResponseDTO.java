package shop.haui_megatech.domain.dto.common;

import lombok.Builder;

@Builder
public record CommonResponseDTO( 
		Boolean result,
		String message
) {
}
