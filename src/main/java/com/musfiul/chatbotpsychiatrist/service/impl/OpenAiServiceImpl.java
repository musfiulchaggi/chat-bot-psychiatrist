package com.musfiul.chatbotpsychiatrist.service.impl;

import com.musfiul.chatbotpsychiatrist.dto.request.openai.MessageRequest;
import com.musfiul.chatbotpsychiatrist.dto.request.openai.OpenAiRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.openai.OpenAiResponse;
import com.musfiul.chatbotpsychiatrist.entity.OpenAi;
import com.musfiul.chatbotpsychiatrist.entity.User;
import com.musfiul.chatbotpsychiatrist.entity.UserAccount;
import com.musfiul.chatbotpsychiatrist.repository.OpenAiRepository;
import com.musfiul.chatbotpsychiatrist.service.OpenAiService;
import com.musfiul.chatbotpsychiatrist.service.UserAccountService;
import com.musfiul.chatbotpsychiatrist.service.UserService;
import com.musfiul.chatbotpsychiatrist.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.util.stream.Collectors;

@Service
public class OpenAiServiceImpl implements OpenAiService {
    private final OpenAiRepository openAiRepository;
    private final UserAccountService userAccountService;
    private final UserService userService;
    private final RestClient restClient;
    private final String TOKEN_API;
    private final String BASE_URL;

    @Autowired
    public OpenAiServiceImpl(
            OpenAiRepository openAiRepository,
            UserAccountService userAccountService,
            UserService userService,
            RestClient restClient,
            @Value("${openai.api.key}")  String tokenApi,
            @Value("${openai.api.url}") String baseUrl) {
        this.openAiRepository = openAiRepository;
        this.userAccountService = userAccountService;
        this.userService = userService;
        this.restClient = restClient;
        this.TOKEN_API = tokenApi;
        this.BASE_URL = baseUrl;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public OpenAi createPromt(OpenAiRequest request) {

        UserAccount userAccount = userAccountService.getByContext();
        User user = userService.getByUserAccountId(userAccount.getId());

        ResponseEntity<OpenAiResponse> response = restClient.post()
                .uri(BASE_URL)
                .body(request)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+ TOKEN_API)
                .retrieve().toEntity(OpenAiResponse.class);

        OpenAiResponse body = response.getBody();

        assert body != null;
        String requestMessage = request.getMessages().stream()
                .filter(messageRequest -> messageRequest.getRole().equals("user"))
                .map(MessageRequest::getContent)
                .collect(Collectors.joining());

        OpenAi openAi = OpenAi.builder()
                .created(DateUtil.timeSecondToDate(body.getCreated()))
                .id(body.getId())
                .model(body.getModel())
                .requestMessage(requestMessage)
                .user(user)
                .responseMessage(body.getChoices().get(0).getMessage().getContent())
                .build();

        return openAiRepository.saveAndFlush(openAi);
    }
}
