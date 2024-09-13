package com.pnu.sursim.domain.gpt.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pnu.sursim.domain.gpt.dto.PromptRequest;
import com.pnu.sursim.domain.gpt.properties.GptProperties;
import com.pnu.sursim.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptService {

    private final GptProperties gptProperties;

    // 모델 사용 가능 여부 확인하기
    public Map<String, Object> isValidModel(String modelName) {
        Map<String, Object> result = new HashMap<>();

        //토큰 정보를 포함한 Header를 가져옵니다.
        HttpHeaders headers = httpHeaders();

        // [STEP2] 통신을 위한 RestTemplate을 구성합니다.
        ResponseEntity<String> response = new RestTemplate()
                .exchange(gptProperties.modelListUrl()+ "/" + modelName, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        try {
            // [STEP3] Jackson을 기반으로 응답값을 가져옵니다.
            ObjectMapper om = new ObjectMapper();
            result = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return result;
    }


    //gpt 물어보기
    public Map<String, Object> prompt(PromptRequest promptRequest) {

        Map<String, Object> resultMap = new HashMap<>();

        //통신을 위한 RestTemplate을 구성합니다.
        HttpEntity<PromptRequest> requestEntity = new HttpEntity<>(promptRequest, httpHeaders());

        System.out.println("requestEntity = " + requestEntity);

        ResponseEntity<String> response = new RestTemplate()
                .exchange(gptProperties.promptUrl(), HttpMethod.POST, requestEntity, String.class);
        try {
            ObjectMapper om = new ObjectMapper();
            resultMap = om.readValue(response.getBody(), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return resultMap;
    }

    private HttpHeaders httpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(gptProperties.secretKey());
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}