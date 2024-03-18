package com.musfiul.chatbotpsychiatrist.dto.request.openai;

import lombok.*;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAiRequest {
    private String model;
    private List<MessageRequest> messages;
}
