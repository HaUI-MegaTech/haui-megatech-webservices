package shop.haui_megatech.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NullRequestException extends RuntimeException {
    private String message;
}
