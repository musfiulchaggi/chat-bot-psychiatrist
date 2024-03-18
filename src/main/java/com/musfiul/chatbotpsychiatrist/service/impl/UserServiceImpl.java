package com.musfiul.chatbotpsychiatrist.service.impl;

import com.musfiul.chatbotpsychiatrist.dto.request.SearchUserRequest;
import com.musfiul.chatbotpsychiatrist.dto.request.UpdateUserRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.UserResponse;
import com.musfiul.chatbotpsychiatrist.entity.User;
import com.musfiul.chatbotpsychiatrist.repository.UserRepository;
import com.musfiul.chatbotpsychiatrist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User create(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse getOneById(String id) {
        return convertUserToUserResponse(findByIdOrThrowNotFound(id));
    }

    @Transactional(readOnly = true)
    @Override
    public User getById(String id) {
        return findByIdOrThrowNotFound(id);
    }

    @Override
    public User getByUserAccountId(String id) {
        return userRepository.findByUserAccountId(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAll(SearchUserRequest request) {
//        Specification<User> specification = UserSpecification.getSpecification(request);
        return userRepository.findAll().stream().map(this::convertUserToUserResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public UserResponse update(UpdateUserRequest request) {
        User currentUser = findByIdOrThrowNotFound(request.getId());
        currentUser.setName(request.getName());
        currentUser.setMobilePhone(request.getMobilePhoneNo());
        currentUser.setAddress(request.getAddress());
        currentUser.setBirthDate(Date.valueOf(request.getBirthDate()));
        userRepository.saveAndFlush(currentUser);
        return convertUserToUserResponse(currentUser);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        User user = findByIdOrThrowNotFound(id);
        userRepository.delete(user);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusById(String id, Boolean status) {
        findByIdOrThrowNotFound(id);
        userRepository.updateStatus(id, status);
    }

    private User findByIdOrThrowNotFound(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }

    private UserResponse convertUserToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .mobilePhoneNo(user.getMobilePhone())
                .address(user.getAddress())
                .status(user.getStatus())
                .userAccountId(user.getUserAccount().getId())
                .build();
    }
}
