package com.pnu.sursim.domain.gpt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record QuestionPrompt(String model,
                             List<ChatRequestMsgDto> messages) {

    public record ChatRequestMsgDto(String role,
                                    String content) {
    }
}