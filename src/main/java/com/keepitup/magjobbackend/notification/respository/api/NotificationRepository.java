package com.keepitup.magjobbackend.notification.respository.api;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, BigInteger> {
    Page<Notification> findAllBySeen(boolean seen, Pageable pageable);
    Page<Notification> findAllBySent(boolean sent, Pageable pageable);
    Page<Notification> findAllByUser(User user, Pageable pageable);
    Page<Notification> findAllByUserAndSeen(User user, Boolean seen, Pageable pageable);
    Page<Notification> findAllByOrganization(Organization organization, Pageable pageable);
    Page<Notification> findAllByOrganizationAndSeen(Organization organization, Boolean seen, Pageable pageable);
    Page<Notification> findAllByMember(Member member, Pageable pageable);
    Page<Notification> findAllByMemberAndSeen(Member member, Boolean seen, Pageable pageable);

}