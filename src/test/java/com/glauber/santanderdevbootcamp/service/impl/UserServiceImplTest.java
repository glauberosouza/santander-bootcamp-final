package com.glauber.santanderdevbootcamp.service.impl;

import com.glauber.santanderdevbootcamp.domain.model.Account;
import com.glauber.santanderdevbootcamp.domain.model.Card;
import com.glauber.santanderdevbootcamp.domain.model.User;
import com.glauber.santanderdevbootcamp.domain.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    public void setUp() {
        // Configurar o comportamento simulado do UserRepository
        User user = new User();
        user.setId(1L);
        user.setName("Glauber");
        user.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        user.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        user.setFeatures(new ArrayList<>());
        user.setNews(new ArrayList<>());

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }
    @AfterEach
    public void afterEach() {
        // Reverter as configurações feitas no setUp
        Mockito.reset(userRepository);
    }

    @Test
    @DisplayName("Deve encontrar um usuário pelo ID")
    public void shouldFindUserById() {
        //ARRANGE:
        //ACT:
        User userById = userService.findById(1L);

        //ASSERT:
        assertEquals(1L, userById.getId());
        assertEquals("Glauber", userById.getName());
    }
    @Test
    @DisplayName("Deve lançar uma exceção se não encontrar um usuário pelo ID")
    public void shouldThrowExceptionIfNotFindUserById(){
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.empty());
        //ASSERT:
        assertThrows(NoSuchElementException.class, () -> userService.findById(2L));
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    public void shouldCreateUser() {
        //ARRANGE:
        User userById = userService.findById(1L);
        //ACT
        userService.create(userById);
        //ASSERTS
        verify(userRepository, times(1)).save(userById);
        // Outros asserts conforme necessário
    }

    @Test
    @DisplayName("Deve lançar uma exceção se a conta já existir")
    public void ShouldThrowExceptionIfThisAccountNumberAlreadyExist(){
        User user = new User();
        user.setId(1L);
        user.setName("Glauber");
        user.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        user.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        user.setFeatures(new ArrayList<>());
        user.setNews(new ArrayList<>());

        Mockito.when(userRepository.save(user)).thenThrow(new IllegalArgumentException());
        //ASSERT:
        assertThrows(IllegalArgumentException.class, () -> userService.create(user));
    }

    @Test
    @DisplayName("Deve atualizar um usuário existente")
    public void shouldUpdateUser() {
        //ARRANGE:
        User byId = userService.findById(1L);

        // User to update
        User userToUpdate = new User();

        userToUpdate.setName("Glauber Souza");
        userToUpdate.setAccount(new Account(null, "12345", "Agência 1", new BigDecimal("1000.00"), new BigDecimal("500.00")));
        userToUpdate.setCard(new Card(null, "987654321", new BigDecimal("2000.00")));
        userToUpdate.setFeatures(new ArrayList<>());
        userToUpdate.setNews(new ArrayList<>());

        //ACT
        userService.updateUser(userToUpdate, 1L);

        //ASSERTS
        Assertions.assertEquals(userToUpdate.getName(), byId.getName());
    }

    @Test
    @DisplayName("Deve excluir um usuário pelo ID")
    public void shouldDeleteUser() {
        User userBydId = userService.findById(1L);
        //ACT
        userService.delete(userBydId.getId());
        List<User> allUsers = userRepository.findAll();
        //ASSERTS
        Assertions.assertEquals(0, allUsers.size());
    }
}