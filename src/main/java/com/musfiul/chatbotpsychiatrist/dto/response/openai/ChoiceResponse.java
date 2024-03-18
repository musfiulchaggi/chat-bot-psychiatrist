package com.musfiul.chatbotpsychiatrist.dto.response.openai;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChoiceResponse {
    private Integer index;
    private MessageResponse message;
    private String finish_reason;
}
