package shop.haui_megatech.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.constant.ErrorMessageConstant;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.util.MessageSourceUtil;

@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {
    private final MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(AbsentRequiredFieldException.class)
    public ResponseEntity<?> handleBlankUsernameException(AbsentRequiredFieldException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .result(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(MismatchedConfirmPasswordException.class)
    public ResponseEntity<?> handleBlankPasswordException(MismatchedConfirmPasswordException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .result(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseUtil.notFound(CommonResponseDTO.builder()
                                                      .result(false)
                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                      .build());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handleWrongPasswordException(WrongPasswordException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .result(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }
}
