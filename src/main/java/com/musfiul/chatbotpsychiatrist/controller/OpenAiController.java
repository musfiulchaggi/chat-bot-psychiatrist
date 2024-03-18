package com.musfiul.chatbotpsychiatrist.controller;

import com.musfiul.chatbotpsychiatrist.constant.APIUrl;
import com.musfiul.chatbotpsychiatrist.dto.request.UserPromptRequest;
import com.musfiul.chatbotpsychiatrist.dto.request.openai.MessageRequest;
import com.musfiul.chatbotpsychiatrist.dto.request.openai.OpenAiRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.UserPromtResponse;
import com.musfiul.chatbotpsychiatrist.entity.OpenAi;
import com.musfiul.chatbotpsychiatrist.service.OpenAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.OPENAI_API)
public class OpenAiController {
    private final OpenAiService openAiService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserPromtResponse> createPrompt(@RequestBody UserPromptRequest request){

        OpenAiRequest openAiRequest = OpenAiRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(List.of(
                        MessageRequest.builder()
                                .role("system")
                                .content("anda adalah seorang psikiater professional, yang berasal dari negara indonesia, " +
                                        "anda sudah menjadi psikiater lebih dari 10 tahun, dan saat ini bekerja di salah satu rumah sakit " +
                                        "terkenal di jakarta, saat ini anda menyediakan layanan konsultasi secara gratis, kepada masyarakat umum.")
                                .build(),
                        MessageRequest.builder()
                                .role("user")
                                .content(request.getPromptRequest())
                                .build()
                ))
                .build();

        OpenAi response = openAiService.createPromt(openAiRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        UserPromtResponse.builder()
                                .date(response.getCreated().toString())
                                .promptRequest(request.getPromptRequest())
                                .promptResponse(response.getResponseMessage())
                                .build()
                );
    }

}
