package shop.haui_megatech.domain.dto;

import lombok.Builder;

public record AuthenticationDTO() {

    public record Request(
            String username,
            String password
    ) {}

    @Builder
    public record Response(
            String token
    ) {}
}
