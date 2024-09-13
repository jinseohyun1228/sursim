package com.pnu.sursim.domain.gpt.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PromptRequest(String model,
                            List<ChatRequestMsgDto> messages){

    record ChatRequestMsgDto(String role,
                             String content) {
    }
}