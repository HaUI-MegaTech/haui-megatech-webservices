package shop.haui_megatech.domain.transfer;

import lombok.Builder;

@Builder
public record ProductDTO ( 
	String name,
	float price
) {
}
