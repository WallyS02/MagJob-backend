package com.keepitup.magjobbackend.user.service.impl;

import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import com.keepitup.magjobbackend.user.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class UserDefaultService implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserDefaultService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Optional<User> find(BigInteger id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> find(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Page<User> findAllByFirstname(String firstname, Pageable pageable) {
        return userRepository.findAllByFirstname(firstname, pageable);
    }

    @Override
    public Page<User> findAllByLastname(String lastname, Pageable pageable) {
        return userRepository.findAllByLastname(lastname, pageable);
    }

    @Override
    public Page<User> findAllByFirstnameAndLastname(String firstname, String lastname, Pageable pageable) {
        return userRepository.findAllByFirstnameAndLastname(firstname, lastname, pageable);
    }

    @Override
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(BigInteger id) {
        userRepository.findById(id).ifPresent(userRepository::delete);
    }

    @Override
    public void update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
