package soomin.park.sajamart.config.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private String message;
    private String code;
    private Map<String, String> validation;


    private ErrorResponse(final ErrorCode code, final Map<String, String> validation) {
        this.message = code.getMessage();
        this.code = code.getCode();
        this.validation = validation;
    }

    public ErrorResponse(final ErrorCode code, final String message, final Map<String, String> validation) {
        this.message = message;
        this.code = code.getCode();
    }

    public static ErrorResponse of(final ErrorCode code, final Map<String, String> validation) {
        return new ErrorResponse(code, validation);
    }

    public static ErrorResponse of(final ErrorCode code, final String message, final Map<String, String> validation) {
        return new ErrorResponse(code, message, validation);
    }
}