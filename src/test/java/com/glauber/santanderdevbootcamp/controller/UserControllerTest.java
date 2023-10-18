package com.glauber.santanderdevbootcamp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.santanderdevbootcamp.SantanderDevBootcampApplication;
import com.glauber.santanderdevbootcamp.controller.converterRequest.UserConverter;
import com.glauber.santanderdevbootcamp.controller.request.UserRequest;
import com.glauber.santanderdevbootcamp.controller.response.UserResponse;
import com.glauber.santanderdevbootcamp.domain.model.Account;
import com.glauber.santanderdevbootcamp.domain.model.Card;
import com.glauber.santanderdevbootcamp.domain.model.User;
import com.glauber.santanderdevbootcamp.domain.repository.UserRepository;
import com.glauber.santanderdevbootcamp.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = SantanderDevBootcampApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve encontrar um usuário pelo Id")
    public void shouldFindUserById() throws Exception {
        // ARRANGE
        var userRequest = new UserRequest();
        userRequest.setName("Glauber");
        userRequest.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        userRequest.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        userRequest.setFeatures(new ArrayList<>());
        userRequest.setNews(new ArrayList<>());

        var userEntity = userConverter.toUserEntity(userRequest);

        var savedUser = userService.create(userEntity);
        // ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andReturn();

        var response = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);

        // ASSERT
        assertEquals(savedUser.getId(), response.getId());
        assertEquals(savedUser.getName(), response.getName());
        assertEquals(savedUser.getAccount().getNumber(), response.getAccount().getNumber());
        assertEquals(savedUser.getCard().getNumber(), response.getCard().getNumber());
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    public void shouldCreateNewUser() throws Exception {
        // ARRANGE
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Glauber");
        userRequest.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        userRequest.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        userRequest.setFeatures(new ArrayList<>());
        userRequest.setNews(new ArrayList<>());

        String userRequestJson = objectMapper.writeValueAsString(userRequest);
        // ACT
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().isCreated())
                .andReturn();

        UserResponse userResponse = objectMapper.readValue(result.getResponse().getContentAsString(), UserResponse.class);
        // ASSERT
        assertEquals("Glauber", userResponse.getName());
    }

    @Test
    @DisplayName("Deve atualizar os dados do usuário")
    public void shouldUpdateUser() throws Exception {
        //ARRANGE
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Glauber");
        userRequest.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        userRequest.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        userRequest.setFeatures(new ArrayList<>());
        userRequest.setNews(new ArrayList<>());

        User userEntity = userConverter.toUserEntity(userRequest);
        userService.create(userEntity);


        var user = new User();
        user.setName("Glauber Souza");
        user.setAccount(new Account(1L, "12345", "Agência 2", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        user.setCard(new Card(1L, "987654123", new BigDecimal("1000.00")));
        user.setFeatures(new ArrayList<>());
        user.setNews(new ArrayList<>());
        //ACT
        User userUpdated = userService.updateUser(user, userEntity.getId());
        var body = objectMapper.writeValueAsString(userUpdated);


        mockMvc.perform(
                put("/users/" + userUpdated.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
        ).andExpect(status().isOk());

        //ASSERT
        Optional<User> userById = userRepository.findById(1L);
        Assertions.assertEquals("Glauber Souza", userById.get().getName());
    }

    @Test
    @DisplayName("Deve deletar um usuário pelo Id")
    public void shouldDeleteUser() throws Exception {
        // ARRANGE
        UserRequest userRequest = new UserRequest();
        userRequest.setName("Glauber");
        userRequest.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        userRequest.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        userRequest.setFeatures(new ArrayList<>());
        userRequest.setNews(new ArrayList<>());

        User userEntity = userConverter.toUserEntity(userRequest);
        userService.create(userEntity);

        Long userId = userEntity.getId();

        // ACT
        mockMvc.perform(
                delete("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());

        // ASSERT
        Optional<User> userById = userRepository.findById(userId);
        Assertions.assertTrue(userById.isEmpty());
    }

}