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
    INCORRECT_QUESTION(HttpStatus.BAD_REQUEST, "There was a problem with the survey questions. Please contact the administrator."),

    //이미지업로드 중 오류
    ERROR_UPLOADING_IMAGE(HttpStatus.BAD_REQUEST, "An error occurred while uploading s3. Please contact the developer."),

    //설문조사에 응답 과정 중 에러
    SURVEY_AGE_AND_USER_AGE_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "Users cannot respond to the survey. The age does not match."),
    SURVEY_GENDER_AND_USER_GENDER_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "Users cannot respond to the survey. The gender does not match."),
    SURVEY_ANSWER_EXISTS(HttpStatus.BAD_REQUEST, "This question has already been answered. You cannot respond twice"),
    SURVEY_CONSENT_REQUIRED(HttpStatus.BAD_REQUEST, "Surveys require consent."),
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "The question cannot be found."),
    INCORRECT_SURVEY_ANSWER(HttpStatus.BAD_REQUEST, "Required response is missing."),
    INCORRECT_QUESTION_TYPE(HttpStatus.BAD_REQUEST, "The question type of the response is incorrect."),
    MULTIPLE_INCORRECT_QUESTIONS(HttpStatus.BAD_REQUEST, "The user's response is incorrect."),
    INCORRECT_OPTIONS_ANSWER(HttpStatus.BAD_REQUEST, "There is no option matching the \"index\" value of the option responded by the user."),

    //get
    GPT_ERROR(HttpStatus.BAD_REQUEST,"An error occurred while requesting gpt."),
    ;

    private final HttpStatus httpStatus;
    private final String errorMessage;


    ErrorCode(HttpStatus httpStatus, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

}
