package com.musfiul.chatbotpsychiatrist.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPromptRequest {
    @NotBlank(message = "Promt required")
    private String promptRequest;
}
