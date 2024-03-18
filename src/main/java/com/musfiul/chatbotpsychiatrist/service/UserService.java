package com.musfiul.chatbotpsychiatrist.service;

import com.musfiul.chatbotpsychiatrist.dto.request.SearchUserRequest;
import com.musfiul.chatbotpsychiatrist.dto.request.UpdateUserRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.UserResponse;
import com.musfiul.chatbotpsychiatrist.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);
    UserResponse getOneById(String id);
    User getById(String id);
    User getByUserAccountId(String id);
    List<UserResponse> getAll(SearchUserRequest request);
    UserResponse update(UpdateUserRequest request);
    void deleteById(String id);
    void updateStatusById(String id, Boolean status);
}
