package com.musfiul.chatbotpsychiatrist.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.musfiul.chatbotpsychiatrist.constant.APIUrl;
import com.musfiul.chatbotpsychiatrist.dto.request.SearchUserRequest;
import com.musfiul.chatbotpsychiatrist.dto.request.UpdateUserRequest;
import com.musfiul.chatbotpsychiatrist.dto.response.UserResponse;
import com.musfiul.chatbotpsychiatrist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.USER_API)
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse userResponse = userService.getOneById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponse>> getAllUser(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "mobilePhoneNo", required = false) String phoneNumber,
            @RequestParam(name = "birthDate", required = false) @JsonFormat(pattern = "yyyy-MM-dd") String birthDate,
            @RequestParam(name = "status", required = false) Boolean status
    ) {
        SearchUserRequest request = SearchUserRequest.builder()
                .name(name)
                .mobilePhoneNumber(phoneNumber)
                .birthDate(birthDate)
                .status(status)
                .build();

        List<UserResponse> customers = userService.getAll(request);

        return ResponseEntity.ok(customers);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponse> updateUser(@RequestBody UpdateUserRequest payload) {
        UserResponse userResponse = userService.update(payload);
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateStatusUser(
            @PathVariable(name = "id") String id,
            @RequestParam(name = "status") Boolean status
    ) {
        userService.updateStatusById(id, status);
        return ResponseEntity.ok("Success Update Data");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteById(@PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.ok("Success Delete Data");
    }
}
