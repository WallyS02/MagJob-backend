package com.keepitup.magjobbackend.rolemember.repository.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface RoleMemberRepository extends JpaRepository<RoleMember, BigInteger> {
    Page<RoleMember> findAllByMember(Member member, Pageable pageable);

    Page<RoleMember> findAllByRole(Role role, Pageable pageable);

    Optional<RoleMember> findByMemberAndRole(Member member, Role role);
}
