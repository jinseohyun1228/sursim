package com.pnu.sursim.global.response;

import com.pnu.sursim.global.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomResponse {

    private HttpStatus httpStatus;
    private int code;
    private Object content;

    private CustomResponse(HttpStatus httpStatus, Object content) {
        this.httpStatus = httpStatus;
        this.code = httpStatus.value();
        this.content = content;
    }

    public static CustomResponse success(Object object) {
        return new CustomResponse(HttpStatus.OK, object);
    }

    public static CustomResponse fail(CustomException customException) {
        return new CustomResponse(customException.getHttpStatus(),customException.getMessage());
    }

    public static CustomResponse fail(Exception exception) {
        return new CustomResponse(HttpStatus.BAD_REQUEST,exception.getMessage());
    }
}
