package com.keepitup.magjobbackend.user.service.impl;

import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDefaultServiceTest {

    @Mock
    private UserRepository userRepository;

    // Change for Keycloak auth later
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDefaultService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testFind() {
        // Arrange
        BigInteger userId = BigInteger.ONE;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.find(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.find(email);

        // Assert
        assertEquals(Optional.of(user), result);
    }

    @Test
    void testFindAllByFirstname() {
        // Arrange
        String firstname = "John";
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAllByFirstname(firstname)).thenReturn(users);

        // Act
        List<User> result = userService.findAllByFirstname(firstname);

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testFindAllByLastname() {
        // Arrange
        String lastname = "Doe";
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAllByLastname(lastname)).thenReturn(users);

        // Act
        List<User> result = userService.findAllByLastname(lastname);

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testFindAllByFirstnameAndLastname() {
        // Arrange
        String firstname = "John";
        String lastname = "Doe";
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepository.findAllByFirstnameAndLastname(firstname, lastname)).thenReturn(users);

        // Act
        List<User> result = userService.findAllByFirstnameAndLastname(firstname, lastname);

        // Assert
        assertEquals(users, result);
    }

    @Test
    void testRegister() {
        // Arrange
        User user = new User();

        // Act
        userService.register(user);

        // Assert
        // Change for Keycloak auth later
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDelete() {
        // Arrange
        BigInteger userId = BigInteger.ONE;
        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository, times(1)).delete(any());
    }

    @Test
    void testUpdate() {
        // Arrange
        User user = new User();

        // Act
        userService.update(user);

        // Assert
        // Change for Keycloak auth later
        verify(passwordEncoder, times(1)).encode(user.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}
