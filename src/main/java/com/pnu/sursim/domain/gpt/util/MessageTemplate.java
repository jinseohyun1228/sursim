package com.pnu.sursim.domain.gpt.util;

import com.pnu.sursim.domain.gpt.dto.QuestionForGpt;
import com.pnu.sursim.domain.gpt.dto.QuestionPrompt;
import com.pnu.sursim.domain.survey.entity.QuestionOption;
import com.pnu.sursim.domain.survey.entity.QuestionType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageTemplate {


    public static Map<String, Object> explanationToQuestion(QuestionForGpt questionPromptRequest) {

        QuestionType questionType = questionPromptRequest.questionType();
        Map<String, Object> questionInformation = new HashMap<>();

        questionInformation.put("설문 조사 제목", questionPromptRequest.surveyTitle());
        questionInformation.put("문항 유형", questionPromptRequest.questionType().getDescription());
        questionInformation.put("문항", questionPromptRequest.text());

        if (questionType.equals(QuestionType.CHECK_CHOICE) || (questionType.equals(QuestionType.MULTIPLE_CHOICE))) {
            Map<String, Object> optionInformation = new HashMap<>();
            List<QuestionForGpt.QuestionOptionRequest> options = questionPromptRequest.questionOption();
            for (int i = options.size() - 1; i >= 0; i--) {
                QuestionForGpt.QuestionOptionRequest questionOptionRequest = options.get(i);
                optionInformation.put(questionOptionRequest.index().toString() + "번", questionOptionRequest.text());
            }
            questionInformation.put("선택할 수 있는 응닶값 정보들", optionInformation);
        }

        if (questionType.equals(QuestionType.SEMANTIC_RATINGS)) {
            Map<String, Object> optionInformation = new HashMap<>();
            optionInformation.put("왼쪽문항", questionPromptRequest.semanticOption().leftEnd());
            optionInformation.put("오른쪽문항", questionPromptRequest.semanticOption().rightEnd());

            questionInformation.put("의미분별척도의 양측 값", optionInformation);
        }

        return questionInformation;
    }

    public static QuestionPrompt makePrompt(String model, String questionInfo) {
        String content = "너는 10년차 리서치 전문가야. 아래 설문정보를 참고해서 문항을 수정해줘," +
                "문항이 요구하는 정보과 문항의 유형이 적절하게 매칭되는지, 문항 유형의 특성에 고려해서 응답이나 질문의 문제는 없는지 봐줘" +
                "어떤 점이 문제인지는 간략하게 알려주고 수정사항을 반영한 예시 결과를 보여줘." +
                "응답 형식은 다음과 같이 고정해서 보내주고, 존댓말을 사용해줘 " +
                "[문제점] : ~~~ " +
                "[수정사항] :~~~ >\n"

                + questionInfo;

        List<QuestionPrompt.ChatRequestMsgDto> massege = new ArrayList<>();
        massege.add(new QuestionPrompt.ChatRequestMsgDto("user", content));
        return new QuestionPrompt(model, massege);
    }

}
