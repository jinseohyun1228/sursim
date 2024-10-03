package com.pnu.sursim.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.httpStatus = errorCode.getHttpStatus();

    }

    public CustomException(ErrorCode errorCode, String error) {
        super(errorCode.getErrorMessage() + error);
        this.httpStatus = errorCode.getHttpStatus();

    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
