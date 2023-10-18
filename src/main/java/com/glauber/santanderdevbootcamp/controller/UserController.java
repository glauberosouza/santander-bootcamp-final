package com.glauber.santanderdevbootcamp.controller;

import com.glauber.santanderdevbootcamp.controller.converterRequest.UserConverter;
import com.glauber.santanderdevbootcamp.controller.request.UserRequest;
import com.glauber.santanderdevbootcamp.controller.response.UserResponse;
import com.glauber.santanderdevbootcamp.domain.model.User;
import com.glauber.santanderdevbootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserConverter userConverter;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter) {
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        var userById = userService.findById(id);
        var userResponse = userConverter.toUserResponse(userById);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserRequest userToCreate) {
        User userEntity = userConverter.toUserEntity(userToCreate);
        userService.create(userEntity);
        UserResponse userResponse = userConverter.toUserResponse(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @RequestBody UserRequest userRequest) {
        User userEntity = userConverter.toUserEntity(userRequest);
        User userUpdated = userService.updateUser(userEntity, userId);
        UserResponse userResponse = userConverter.toUserResponse(userUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
