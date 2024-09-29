package com.pnu.sursim.domain.gpt.controller;

import com.pnu.sursim.domain.gpt.dto.QuestionForGpt;
import com.pnu.sursim.domain.gpt.service.GptService;
import com.pnu.sursim.global.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class GptController {

    private final GptService gptService;

    //설문 문항 검사하기
    @PostMapping("/prompt")
    public CustomResponse helpQuestion(@RequestBody QuestionForGpt questionRequest) {
        String result = gptService.helpQuestion(questionRequest);
        return CustomResponse.success(result);
    }
}
