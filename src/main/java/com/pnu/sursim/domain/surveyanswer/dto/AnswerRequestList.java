package com.pnu.sursim.domain.surveyanswer.dto;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.RequiredOption;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnswerRequestList {

    private final HashMap<Long, AnswerRequest> answerRequestHashMap;

    public AnswerRequestList(List<AnswerRequest> answerList) {
        this.answerRequestHashMap = answerList.stream()
                .collect(Collectors.toMap(
                        AnswerRequest::getId,                                               //키는 id로
                        answerRequest -> answerRequest,                                     //값은 오브젝트 자체
                        // 중복된 키가 있을 경우 예외 발생
                        (existing, replacement) -> {
                            throw new CustomException(ErrorCode.INCORRECT_QUESTION );
                        },
                        // 결과를 HashMap으로 수집
                        HashMap::new
                ));
    }

    // 문항 ID로 존재 여부 확인 메서드
    private void validAnswerOrThrow(Question question) {

        //해당 응답이 존재하지 않지만, 선택인 경우 -> 정상
        if ((!answerRequestHashMap.containsKey(question.getId())) && (question.getRequiredOption() == RequiredOption.OPTIONAL)) {
            return;
        }

        //해당 응답이 존재하지 않고, 필수인 경우 -> 에러 발생
        if ((!answerRequestHashMap.containsKey(question.getId())) && (question.getRequiredOption() == RequiredOption.REQUIRED)) {
            throw new CustomException(ErrorCode.INCORRECT_QUESTION );
        }

        //question기준으로 적절한 응답값찾기
        AnswerRequest answerRequest = answerRequestHashMap.get(question.getId());
        if (!answerRequest.validQuestionType(question)){
            throw new CustomException(ErrorCode.INCORRECT_QUESTION );
        }

    }

    // 문항 ID로 문항을 반환하는 메서드
    public AnswerRequest getAnswerById(Question question) {
        return Optional.ofNullable(answerRequestHashMap.get(question.getId()))
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND ));
    }

    public AnswerRequestList validateAnswers(List<Question> questionList) {
        List<String> errors = new ArrayList<>();

        questionList.forEach(question -> {
            try {
                this.validAnswerOrThrow(question);
            } catch (CustomException e) {
                // 에러가 발생한 문항에 대한 정보를 저장
                errors.add("Question ID: " + question.getId() + " - " + e.getMessage());
            }
        });

        if (!errors.isEmpty()) {
            // 에러 목록을 하나의 예외로 묶어 던지기
            throw new CustomException(ErrorCode.MULTIPLE_INCORRECT_QUESTIONS );
        }
        return this;
    }

}
