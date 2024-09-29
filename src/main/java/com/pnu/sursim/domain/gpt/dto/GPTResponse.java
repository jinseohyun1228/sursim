package com.pnu.sursim.domain.gpt.dto;

import lombok.Getter;

import java.util.List;


@Getter
public class GPTResponse{

    private String id;
    private String object;
    private int created;
    private String model;
    private List<Choice> choices;
    private Usage usage;

    public String getResponse() {
        return this.getChoices().get(0).getMessage().getContent()
                .replace("**", "")
                .replace("\n", "")
                .replace("```","")
                .replace("\\\\","")
                .replace("\\\"", "\"")
                .replace("\\", "");
    }

    @Getter
    public static class Choice {
        private int index;
        private Message message;
        private String finish_reason;

    }
    @Getter
    public static class Message {
        private String role;
        private String content;

    }

    @Getter
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;

    }

}
