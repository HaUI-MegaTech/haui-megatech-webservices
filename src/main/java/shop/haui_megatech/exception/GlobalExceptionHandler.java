package shop.haui_megatech.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.haui_megatech.domain.dto.common.CommonResponseDTO;
import shop.haui_megatech.utility.MessageSourceUtil;
import shop.haui_megatech.utility.ResponseUtil;

import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSourceUtil messageSourceUtil;

    @ExceptionHandler(AbsentRequiredFieldException.class)
    public ResponseEntity<?> handleBlankUsernameException(AbsentRequiredFieldException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(MismatchedConfirmPasswordException.class)
    public ResponseEntity<?> handleBlankPasswordException(MismatchedConfirmPasswordException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseUtil.notFound(CommonResponseDTO.builder()
                                                      .success(false)
                                                      .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                      .build());
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<?> handleWrongPasswordException(WrongPasswordException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(InvalidRequestParamException.class)
    public ResponseEntity<?> handleInvalidRequestParamException(InvalidRequestParamException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(NullRequestException.class)
    public ResponseEntity<?> handleNullRequestException(NullRequestException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<?> handleDuplicateUsernameException(DuplicateUsernameException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(Objects.requireNonNull(ex.getFieldError())
                                                                                                     .getDefaultMessage()))
                                                        .build());
    }

    @ExceptionHandler(MalformedFileException.class)
    public ResponseEntity<?> handleMalformedFileException(MalformedFileException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<?> handleAppException(AppException ex) {
        return ResponseUtil.badRequest(CommonResponseDTO.builder()
                                                        .success(false)
                                                        .message(messageSourceUtil.getMessage(ex.getMessage()))
                                                        .build());
    }
}
