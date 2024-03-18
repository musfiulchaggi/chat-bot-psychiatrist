package com.musfiul.chatbotpsychiatrist.dto.response;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPromtResponse {
    private String promptRequest;
    private String promptResponse;
    private String date;
}
