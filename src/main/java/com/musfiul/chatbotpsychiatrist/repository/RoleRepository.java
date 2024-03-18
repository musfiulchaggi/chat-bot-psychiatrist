package com.musfiul.chatbotpsychiatrist.repository;

import com.musfiul.chatbotpsychiatrist.constant.UserRole;
import com.musfiul.chatbotpsychiatrist.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
