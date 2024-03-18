package com.musfiul.chatbotpsychiatrist.dto.response.openai;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAiResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<ChoiceResponse> choices;
    private Usage usage;
    private String system_fingerprint;
}
