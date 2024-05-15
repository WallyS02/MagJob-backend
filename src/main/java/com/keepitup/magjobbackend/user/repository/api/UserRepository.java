package com.keepitup.magjobbackend.user.repository.api;

import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAllByFirstname(String firstname);
    List<User> findAllByLastname(String lastname);
    List<User> findAllByFirstnameAndLastname(String firstname, String lastname);

}
