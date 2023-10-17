package com.glauber.santanderdevbootcamp.controller.converterRequest;

import com.glauber.santanderdevbootcamp.controller.request.UserRequest;
import com.glauber.santanderdevbootcamp.controller.response.UserResponse;
import com.glauber.santanderdevbootcamp.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toUserEntity(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setAccount(userRequest.getAccount());
        user.setCard(userRequest.getCard());
        user.setFeatures(userRequest.getFeatures());
        user.setNews(userRequest.getNews());
        return user;
    }

    public UserResponse toUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setAccount(user.getAccount());
        userResponse.setCard(user.getCard());
        userResponse.setFeatures(user.getFeatures());
        userResponse.setNews(user.getNews());
        return userResponse;
    }
}
