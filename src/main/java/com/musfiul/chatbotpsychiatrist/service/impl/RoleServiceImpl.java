package com.musfiul.chatbotpsychiatrist.service.impl;

import com.musfiul.chatbotpsychiatrist.constant.UserRole;
import com.musfiul.chatbotpsychiatrist.entity.Role;
import com.musfiul.chatbotpsychiatrist.repository.RoleRepository;
import com.musfiul.chatbotpsychiatrist.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(
                        () -> roleRepository.saveAndFlush(Role.builder()
                                .role(role)
                                .build())
                );
    }
}
