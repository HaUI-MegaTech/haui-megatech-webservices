package shop.haui_megatech.domain.transfer.common;

import lombok.Builder;

@Builder
public record CommonResponseDTO( 
		Boolean result,
		String message
) {
}
