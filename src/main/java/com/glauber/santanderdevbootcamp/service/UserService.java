package com.glauber.santanderdevbootcamp.service;

import com.glauber.santanderdevbootcamp.domain.model.User;

public interface UserService {
    User findById(Long id);

    User create(User userToCreate);
}
