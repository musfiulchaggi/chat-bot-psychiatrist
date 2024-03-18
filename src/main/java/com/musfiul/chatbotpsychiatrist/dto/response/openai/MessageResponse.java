package com.musfiul.chatbotpsychiatrist.dto.response.openai;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private String role;
    private String content;
}
