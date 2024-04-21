package shop.haui_megatech.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.utility.MessageSourceUtil;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
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

    @ExceptionHandler(InvalidRequestParamException.class)
    public ResponseEntity<?> handleInvalidRequestParamException(InvalidRequestParamException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .result(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(NullRequestException.class)
    public ResponseEntity<?> handleNullRequestException(NullRequestException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .result(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .result(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

}
