package com.musfiul.chatbotpsychiatrist.controller;

import com.musfiul.chatbotpsychiatrist.constant.APIUrl;
import com.musfiul.chatbotpsychiatrist.dto.request.auth.AuthRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.auth.LoginResponse;
import com.musfiul.chatbotpsychiatrist.dto.response.auth.RegisterResponse;
import com.musfiul.chatbotpsychiatrist.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = APIUrl.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody AuthRequest request) {
        RegisterResponse register = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @PostMapping(path = "/register/admin",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request) {
        RegisterResponse register = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(register);
    }

    @PostMapping(path = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.ok(loginResponse);
    }

}
