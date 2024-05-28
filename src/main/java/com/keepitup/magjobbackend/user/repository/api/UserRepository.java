package com.keepitup.magjobbackend.user.repository.api;

import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, BigInteger> {
    Optional<User> findByEmail(String email);
    Page<User> findAllByFirstname(String firstname, Pageable pageable);
    Page<User> findAllByLastname(String lastname, Pageable pageable);
    Page<User> findAllByFirstnameAndLastname(String firstname, String lastname, Pageable pageable);
}
