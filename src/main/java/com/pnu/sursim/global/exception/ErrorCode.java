package com.pnu.sursim.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //회원가입
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "This email already exists."),
    EMPTY_VALUE_NOT_POSSIBLE(HttpStatus.BAD_REQUEST, "Contains an invalid value."),

    //로그인
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "Invalid email and password."),
    KAKAO_LOGIN_ERROR_NO_USER(HttpStatus.BAD_REQUEST, "User information could not be received from Kakao. The token may be incorrect."),

    //토큰
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "Your session has expired."),
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST, "There is no token information."),

    //회원
    USER_ERROR(HttpStatus.BAD_REQUEST, "There is no member information."),

    //설문조사관련응답
    SURVEY_ALREADY_HAS_REWARDS(HttpStatus.BAD_REQUEST, "The survey already has reward information. Please contact the developer or administrator."),
    SURVEY_UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, "The logged in user does not have access to the survey."),
    SURVEY_NO_REWARDS(HttpStatus.BAD_REQUEST, "There is no reward information in this survey.  Please contact the developer."),
    SURVEY_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST, "There is no survey matching that ID value."),
    INCORRECT_CHOICE_QUESTION(HttpStatus.BAD_REQUEST, "An error occurred in the CHOICE_QUESTION. This may be a wrong survey. Please contact the developer."),
    INCORRECT_SEMANTIC_QUESTIONS(HttpStatus.BAD_REQUEST, "An error occurred in the SEMANTIC_QUESTIONS. This may be a wrong survey. Please contact the developer."),

    //이미지업로드 중 오류
    ERROR_UPLOADING_IMAGE(HttpStatus.BAD_REQUEST, "An error occurred while uploading s3. Please contact the developer."),
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;


    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}
