package com.musfiul.chatbotpsychiatrist.repository;

import com.musfiul.chatbotpsychiatrist.entity.OpenAi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenAiRepository extends JpaRepository<OpenAi, String> {
}
