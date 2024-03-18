package com.musfiul.chatbotpsychiatrist.service;

import com.musfiul.chatbotpsychiatrist.constant.UserRole;
import com.musfiul.chatbotpsychiatrist.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole role);
}
