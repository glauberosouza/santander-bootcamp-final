package com.glauber.santanderdevbootcamp.service.impl;

import com.glauber.santanderdevbootcamp.controller.exception.EntityNotFoundException;
import com.glauber.santanderdevbootcamp.domain.model.User;
import com.glauber.santanderdevbootcamp.domain.repository.UserRepository;
import com.glauber.santanderdevbootcamp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

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
    @Transactional
    public User create(User userToCreate) {
        if (repository.existsByAccountNumber(userToCreate.getAccount().getNumber())) {
            throw new IllegalArgumentException("This Account number already exist");
        }
        return repository.save(userToCreate);
    }

    @Override
    @Transactional
    public User updateUser(User userToUpdate, Long userId) {

        User userById = repository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + userId));


        userById.setName(userToUpdate.getName());
        userById.setAccount(userToUpdate.getAccount());
        userById.setCard(userToUpdate.getCard());
        userById.setFeatures(userToUpdate.getFeatures());
        userById.setNews(userToUpdate.getNews());

        return repository.save(userById);
    }


    @Override
    @Transactional
    public void delete(Long userId) {
        Optional<User> userById = repository.findById(userId);
        if (userById.isPresent()) {
            repository.deleteById(userId);
        } else {
            throw new EntityNotFoundException("Não foi localizado um usuário com o id: " + userId);
        }
    }
}
