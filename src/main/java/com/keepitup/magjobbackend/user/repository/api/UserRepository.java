package com.keepitup.magjobbackend.user.repository.api;

import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {
    Optional<User> findByEmail(String email);

    Optional<User> findByExternalId(String externalId);
    List<User> findAllByFirstname(String firstname);
    List<User> findAllByLastname(String lastname);
    List<User> findAllByFirstnameAndLastname(String firstname, String lastname);

}
