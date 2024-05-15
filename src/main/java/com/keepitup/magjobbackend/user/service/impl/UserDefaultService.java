package com.keepitup.magjobbackend.user.service.impl;

import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import com.keepitup.magjobbackend.user.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDefaultService implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserDefaultService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> find(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> find(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllByFirstname(String firstname) {
        return userRepository.findAllByFirstname(firstname);
    }

    @Override
    public List<User> findAllByLastname(String lastname) {
        return userRepository.findAllByLastname(lastname);
    }

    @Override
    public List<User> findAllByFirstnameAndLastname(String firstname, String lastname) {
        return userRepository.findAllByFirstnameAndLastname(firstname, lastname);
    }

    @Override
    public void register(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }
}
