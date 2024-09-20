package com.pnu.sursim.domain.gpt.properties;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai") //내부적으로 세터를 이용해서 등록하게 한다.
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record GptProperties(
        String secretKey,
        String modelUrl,
        String modelListUrl,
        String promptUrl) {
}