package com.pnu.sursim.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //회원가입
    EMAIL_EXISTS(HttpStatus.BAD_REQUEST, "이 이메일은 이미 존재합니다.", "AU001"),
    EMPTY_VALUE_NOT_POSSIBLE(HttpStatus.BAD_REQUEST, "잘못된 값이 포함되어 있습니다.", "AU002"),

    //로그인
    LOGIN_ERROR(HttpStatus.BAD_REQUEST, "이메일 또는 비밀번호가 잘못되었습니다.", "AU003"),
    KAKAO_LOGIN_ERROR_NO_USER(HttpStatus.BAD_REQUEST, "카카오에서 사용자 정보를 받을 수 없습니다. 토큰이 잘못되었을 수 있습니다.", "AU004"),
    LOGIN_REQUIRED(HttpStatus.BAD_REQUEST, "로그인이 필요합니다.", "AU005"),

    //토큰
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "세션이 만료되었습니다.", "AU006"),
    TOKEN_EMPTY(HttpStatus.BAD_REQUEST, "토큰 정보가 없습니다.", "AU007"),

    //회원
    USER_ERROR(HttpStatus.BAD_REQUEST, "회원 정보를 찾을 수 없습니다.", "AU008"),

    //설문조사 관련 응답
    SURVEY_ALREADY_HAS_REWARDS(HttpStatus.BAD_REQUEST, "이 설문에는 이미 리워드 정보가 있습니다. 개발자 또는 관리자에게 문의하십시오.", "SR001"),
    SURVEY_UNAUTHORIZED_USER(HttpStatus.BAD_REQUEST, "로그인한 사용자는 이 설문에 접근할 권한이 없습니다.", "SR002"),
    SURVEY_NO_REWARDS(HttpStatus.BAD_REQUEST, "이 설문에는 리워드 정보가 없습니다. 개발자에게 문의하십시오.", "SR003"),
    SURVEY_DOES_NOT_EXIST(HttpStatus.BAD_REQUEST, "해당 ID 값에 맞는 설문이 존재하지 않습니다.", "SR004"),
    INCORRECT_CHOICE_QUESTION(HttpStatus.BAD_REQUEST, "선택형 질문에서 오류가 발생했습니다. 잘못된 설문일 수 있습니다. 개발자에게 문의하십시오.", "SR005"),
    INCORRECT_SEMANTIC_QUESTIONS(HttpStatus.BAD_REQUEST, "의미 질문에서 오류가 발생했습니다. 잘못된 설문일 수 있습니다. 개발자에게 문의하십시오.", "SR006"),
    INCORRECT_QUESTION(HttpStatus.BAD_REQUEST, "설문 질문에 문제가 발생했습니다. 관리자에게 문의하십시오.", "SR007"),

    REWARD_REQUEST_INVALID(HttpStatus.BAD_REQUEST, "리워드 요청 값이 유효하지 않습니다.", "SR008"),

    //이미지 업로드 중 오류
    ERROR_UPLOADING_IMAGE(HttpStatus.BAD_REQUEST, "S3에 이미지를 업로드하는 중 오류가 발생했습니다. 개발자에게 문의하십시오.", "IM001"),

    //설문조사에 응답 과정 중 에러
    SURVEY_AGE_AND_USER_AGE_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "사용자는 이 설문에 응답할 수 없습니다. 나이가 맞지 않습니다.", "SR009"),
    SURVEY_GENDER_AND_USER_GENDER_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "사용자는 이 설문에 응답할 수 없습니다. 성별이 맞지 않습니다.", "SR010"),
    SURVEY_ANSWER_EXISTS(HttpStatus.BAD_REQUEST, "이 질문은 이미 응답되었습니다. 두 번 응답할 수 없습니다.", "SR011"),
    SURVEY_CONSENT_REQUIRED(HttpStatus.BAD_REQUEST, "설문 응답에는 동의가 필요합니다.", "SR012"),
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 질문을 찾을 수 없습니다.", "SR013"),
    INCORRECT_SURVEY_ANSWER(HttpStatus.BAD_REQUEST, "필수 응답이 누락되었습니다.", "SR014"),
    INCORRECT_QUESTION_TYPE(HttpStatus.BAD_REQUEST, "응답의 질문 유형이 올바르지 않습니다.", "SR015"),
    MULTIPLE_INCORRECT_QUESTIONS(HttpStatus.BAD_REQUEST, "사용자의 응답이 잘못되었습니다.", "SR016"),
    INCORRECT_OPTIONS_ANSWER(HttpStatus.BAD_REQUEST, "사용자가 응답한 옵션의 \"index\" 값과 일치하는 옵션이 없습니다.", "SR017"),

    //GPT 관련 에러
    GPT_ERROR(HttpStatus.BAD_REQUEST, "GPT 요청 중 오류가 발생했습니다.", "GP001");


    private final HttpStatus httpStatus;
    private final String errorMessage;
    private final String code;


    ErrorCode(HttpStatus httpStatus, String errorMessage, String code) {
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.code = code;
    }

}
