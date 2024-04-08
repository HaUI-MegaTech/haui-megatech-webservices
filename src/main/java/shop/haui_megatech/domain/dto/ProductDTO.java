package shop.haui_megatech.domain.dto;

import lombok.Builder;

@Builder
public record ProductDTO ( 
	String name,
	float price
) {
}
