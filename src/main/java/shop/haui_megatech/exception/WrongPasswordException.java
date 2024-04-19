package shop.haui_megatech.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class WrongPasswordException extends RuntimeException {
    private String message;
}
