package com.keepitup.magjobbackend.user.service.api;

import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Optional<User> find(UUID id);

    Optional<User> find(String email);

    Page<User> findAllByFirstname(String firstname, Pageable pageable);

    Page<User> findAllByLastname(String lastname, Pageable pageable);

    Page<User> findAllByFirstnameAndLastname(String firstname, String lastname, Pageable pageable);

    void register(User user);
    void delete(UUID id);

    void update(User user);
}
