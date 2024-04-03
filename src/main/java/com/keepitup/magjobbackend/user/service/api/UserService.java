package com.keepitup.magjobbackend.user.service.api;

import com.keepitup.magjobbackend.user.entity.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> find(BigInteger id);

    Optional<User> find(String email);

    Optional<User> findByExternalId(String externalId);

    List<User> findAllByFirstname(String firstname);

    List<User> findAllByLastname(String lastname);

    List<User> findAllByFirstnameAndLastname(String firstname, String lastname);

    void register(User user);

    void delete(BigInteger id);

    void deleteByExternalId(String externalId);

    void update(User user);
}
