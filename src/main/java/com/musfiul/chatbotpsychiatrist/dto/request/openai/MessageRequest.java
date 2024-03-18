package com.musfiul.chatbotpsychiatrist.dto.request.openai;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {
    private String role;
    private String content;
}
