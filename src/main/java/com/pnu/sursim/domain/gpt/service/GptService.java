package com.pnu.sursim.domain.gpt.service;

import com.pnu.sursim.domain.gpt.dto.GPTResponse;
import com.pnu.sursim.domain.gpt.dto.QuestionForGpt;
import com.pnu.sursim.domain.gpt.dto.QuestionPrompt;
import com.pnu.sursim.domain.gpt.properties.GptProperties;
import com.pnu.sursim.domain.gpt.util.MessageTemplate;
import com.pnu.sursim.global.exception.CustomException;
import com.pnu.sursim.global.exception.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service

public class GptService {

    public GptService(GptProperties gptProperties) {
        this.gptProperties = gptProperties;
        this.restClient = RestClient.create();
    }

    private final GptProperties gptProperties;
    private final RestClient restClient;


    //gpt 물어보기
    public String helpQuestion(QuestionForGpt questionRequest) {

        String questionInfo = MessageTemplate.explanationToQuestion(questionRequest).toString();
        QuestionPrompt questionPrompt = MessageTemplate.makePrompt(gptProperties.model(), questionInfo);

        System.out.println("questionPrompt = " + questionPrompt);
        //통신을 위한 RestTemplate을 구성합니다.

        GPTResponse gptResponse = restClient.post()
                .uri(gptProperties.promptUrl())
                .header("Authorization", "Bearer " + gptProperties.secretKey())
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .body(questionPrompt)
                .retrieve()
                .toEntity(GPTResponse.class)
                .getBody();

        if (gptResponse == null) {
            throw new CustomException(ErrorCode.GPT_ERROR); // 커스텀 예외 사용
        }

        return gptResponse.getResponse();

    }


}