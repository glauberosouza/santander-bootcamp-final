package com.glauber.santanderdevbootcamp.service.impl;

import com.glauber.santanderdevbootcamp.domain.model.User;
import com.glauber.santanderdevbootcamp.domain.repository.UserRepository;
import com.glauber.santanderdevbootcamp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    @Override
    public User create(User userToCreate) {
        if (repository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("This Account number already exist");
        }
        return repository.save(userToCreate);
    }
}
