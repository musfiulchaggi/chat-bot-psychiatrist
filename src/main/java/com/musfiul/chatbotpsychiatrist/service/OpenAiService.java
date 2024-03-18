package com.musfiul.chatbotpsychiatrist.service;

import com.musfiul.chatbotpsychiatrist.dto.request.openai.OpenAiRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.openai.OpenAiResponse;
import com.musfiul.chatbotpsychiatrist.entity.OpenAi;

public interface OpenAiService {
    OpenAi createPromt(OpenAiRequest request);
}
