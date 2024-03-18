package com.musfiul.chatbotpsychiatrist.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private String id;
    private String name;
    private String mobilePhoneNo;
    private String address;
    private Boolean status;
    private String userAccountId;
}
