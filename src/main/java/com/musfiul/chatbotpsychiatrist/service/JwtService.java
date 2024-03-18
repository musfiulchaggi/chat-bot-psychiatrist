package com.musfiul.chatbotpsychiatrist.service;

import com.musfiul.chatbotpsychiatrist.dto.response.auth.JwtClaims;
import com.musfiul.chatbotpsychiatrist.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
