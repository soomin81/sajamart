package soomin.park.sajamart.config.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import soomin.park.sajamart.config.error.exception.BusinessBaseException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 잡아서 처리
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @RequestMapping(produces = MediaTypes.HAL_JSON_VALUE)
    protected ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        log.error("HttpRequestMethodNotSupportedException", e);

        Map<String, String> validation = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validation.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return createErrorResponseEntity(ErrorCode.BAD_REQUEST, validation);
    }

    @ExceptionHandler(BusinessBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(BusinessBaseException e) {
        log.error("BusinessException", e);
        return createErrorResponseEntity(e.getErrorCode(), null);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error("Exception", e);
        return createErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR, null);
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode, Map<String, String> validation) {
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode, validation),
                errorCode.getStatus());
    }
}