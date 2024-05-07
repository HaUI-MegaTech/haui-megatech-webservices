package shop.haui_megatech.domain.dto;

public record BrandDTO(

) {
    public record Response(
            Integer id,
            String name,
            String image
    ) {}
}
