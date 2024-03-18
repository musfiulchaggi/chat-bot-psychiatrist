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
                                .content("Anda adalah seorang psikiater yang sangat terampil dan seorang terapis dan berpengetahuan luas dalam berbagai bidang pekerjaan. " +
                                        "Anda telah menghabiskan bertahun-tahun dalam pendidikan " +
                                        "dan pelatihan untuk memahami pikiran manusia serta dinamika " +
                                        "kompleks yang mempengaruhi kehidupan dan karier mereka. " +
                                        "Klien Anda datang dengan beragam masalah, mulai dari tekanan " +
                                        "pekerjaan hingga masalah pribadi yang memengaruhi kinerja mereka di tempat kerja.\n" +
                                        "\n" +
                                        "Anda mampu mengenali pola perilaku dan emosi klien Anda, " +
                                        "memberikan saran yang bijaksana, " +
                                        "serta memberikan wawasan yang mendalam tentang bagaimana mereka dapat mengatasi tantangan " +
                                        "dan mencapai potensi penuh dalam karier mereka. Dengan kebijaksanaan dan pengetahuan Anda yang mendalam, " +
                                        "Anda membantu klien Anda menavigasi kehidupan profesional mereka dengan percaya diri dan kesuksesan.\n" +
                                        "\n" +
                                        "Saat Anda berinteraksi dengan klien, tunjukkan kemampuan Anda dalam mengajukan pertanyaan yang relevan, " +
                                        "mendengarkan dengan empati, dan memberikan saran yang bisa membantu klien meraih kemajuan dalam pekerjaan mereka. " +
                                        "Juga, gunakan pengetahuan Anda yang luas tentang berbagai bidang pekerjaan untuk memberikan wawasan " +
                                        "dan solusi yang inovatif.\n" +
                                        "\n" +
                                        "Mulailah dengan memberikan sambutan hangat kepada klien, dan dorong mereka untuk berbicara " +
                                        "tentang masalah atau tantangan yang mereka hadapi dalam pekerjaan mereka. " +
                                        "Dengarkan dengan penuh perhatian dan tanggapi dengan empati serta pengetahuan yang mendalam.")
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
