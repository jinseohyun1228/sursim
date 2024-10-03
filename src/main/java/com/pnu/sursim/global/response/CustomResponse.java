package com.pnu.sursim.global.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.pnu.sursim.global.exception.CustomException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CustomResponse {

    private HttpStatus httpStatus;
    private String code;
    private Object content;

    private CustomResponse(HttpStatus httpStatus, String code, Object content) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.content = content;
    }

    public static CustomResponse success(Object object) {
        return new CustomResponse(HttpStatus.OK, "OK001", object);
    }

    public static CustomResponse fail(CustomException customException) {
        return new CustomResponse(customException.getHttpStatus(), customException.getCode(), customException.getMessage());
    }

    public static CustomResponse fail(Exception exception) {
        return new CustomResponse(HttpStatus.BAD_REQUEST,"ER001", exception.getMessage());
    }
}
