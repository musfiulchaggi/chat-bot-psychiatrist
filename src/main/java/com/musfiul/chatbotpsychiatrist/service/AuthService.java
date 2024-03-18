package com.musfiul.chatbotpsychiatrist.service;

import com.musfiul.chatbotpsychiatrist.dto.request.auth.AuthRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.auth.LoginResponse;
import com.musfiul.chatbotpsychiatrist.dto.response.auth.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest request);

    RegisterResponse registerAdmin(AuthRequest request);

    LoginResponse login(AuthRequest request);
}
