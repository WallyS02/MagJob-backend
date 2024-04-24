package com.keepitup.magjobbackend.rolemember.repository.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoleMemberRepository extends JpaRepository<RoleMember, BigInteger> {
    List<RoleMember> findAllByMember(Member member);

    List<RoleMember> findAllByRole(Role role);

    Optional<RoleMember> findByMemberAndRole(Member member, Role role);
}
