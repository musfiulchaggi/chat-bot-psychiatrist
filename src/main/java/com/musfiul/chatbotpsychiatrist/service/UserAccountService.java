package com.musfiul.chatbotpsychiatrist.service;

import com.musfiul.chatbotpsychiatrist.entity.UserAccount;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    UserAccount getByUserId(String id);

    UserAccount getByContext();

}
