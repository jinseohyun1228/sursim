package com.pnu.sursim.domain.gpt.controller;

import com.pnu.sursim.domain.gpt.dto.PromptRequest;
import com.pnu.sursim.domain.gpt.service.GptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class GptController {

    private final GptService gptService;

    //해당 모델 사용가능한지 판단하기
    @GetMapping("/prompt")
    public ResponseEntity<Map<String, Object>> isValidModel(@RequestParam(name = "modelName", defaultValue = "gpt-3.5-turbo") String modelName) {
        Map<String, Object> result = gptService.isValidModel(modelName);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    //설문 문항 검사하기
    @PostMapping("/prompt")
    public ResponseEntity<Map<String, Object>> selectPrompt(@RequestBody PromptRequest promptRequest) {
        Map<String, Object> result = gptService.prompt(promptRequest);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
