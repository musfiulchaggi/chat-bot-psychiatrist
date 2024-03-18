package com.musfiul.chatbotpsychiatrist.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequest {
    private String id;
    private String name;
    private String mobilePhoneNo;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birthDate;
}
