package com.pnu.sursim.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //회원가입
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST,"This email already exists."),
    EMPTY_VALUE_NOT_POSSIBLE(HttpStatus.BAD_REQUEST,"Contains an invalid value."),

    //로그인
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "Invalid email and password."),
    KAKAO_LOGIN_ERROR_NO_USER(HttpStatus.BAD_REQUEST, "User information could not be received from Kakao. The token may be incorrect."),

    //토큰
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST,"Your session has expired."),
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST,"There is no token information."),

    //회원
    USER_ERROR(HttpStatus.BAD_REQUEST,"There is no member information."),


    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;


    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}
