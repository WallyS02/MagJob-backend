package com.keepitup.magjobbackend.chat.repository.api;

import com.keepitup.magjobbackend.chat.entity.Chat;
import com.keepitup.magjobbackend.organization.entity.Organization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, BigInteger> {
    Optional<Chat> findByTitle(String title);

    Page<Chat> findAllByOrganization(Organization organization, Pageable pageable);
}
