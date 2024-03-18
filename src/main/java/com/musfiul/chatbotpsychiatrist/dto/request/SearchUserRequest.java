package com.musfiul.chatbotpsychiatrist.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserRequest {
    private String name;
    private String mobilePhoneNumber;
    private String birthDate;
    private Boolean status;
}
