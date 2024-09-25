package com.pnu.sursim.domain.surveyanswer.dto;

import com.pnu.sursim.domain.survey.entity.Question;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AnswerRequestList {

    private final HashMap<Long, QuestionAnswerRequest> answerRequestHashMap;

    public AnswerRequestList(List<QuestionAnswerRequest> answerList) {
        this.answerRequestHashMap = answerList.stream()
                .collect(Collectors.toMap(
                        QuestionAnswerRequest::getId,                    //키는 id로
                        questionAnswerRequest -> questionAnswerRequest,               //값은 오브젝트 자체
                        (existing, replacement) -> {throw new CustomException(ErrorCode.INCORRECT_QUESTION);}, // 중복된 키가 있을 경우 예외 발생
                        HashMap::new   // 결과를 HashMap으로 수집
                ));
    }

    // 문항 ID로 존재 여부 확인 메서드
    public boolean hasAnswer(Question question) {
        return answerRequestHashMap.containsKey(question.getId());
    }

    // 문항 ID로 문항을 반환하는 메서드
    public QuestionAnswerRequest getAnswerById(Long questionId) {
        return Optional.ofNullable(answerRequestHashMap.get(questionId))
                .orElseThrow(() -> new CustomException(ErrorCode.QUESTION_NOT_FOUND));
    }

}
