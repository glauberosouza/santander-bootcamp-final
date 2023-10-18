package com.glauber.santanderdevbootcamp.service;

import com.glauber.santanderdevbootcamp.domain.model.User;

public interface UserService {
    User findById(Long id);

    User create(User userToCreate);

    User updateUser(User userToUpdate, Long userId);

    void delete(Long userId);
}
