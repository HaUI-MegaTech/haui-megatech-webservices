package shop.haui_megatech.domain.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record MailDTO(
        String to,
        String subject,
        String content,
        Map<String, Object> variables
) {
}
