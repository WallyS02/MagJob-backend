package com.keepitup.magjobbackend.rolemember.service.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface RoleMemberService {

    Optional<RoleMember> find(BigInteger id);

    Optional<RoleMember> findByMemberAndRole(Member member, Role role);

    List<RoleMember> findAll();

    List<RoleMember> findAllByMember(Member member);

    List<RoleMember> findAllByRole(Role role);

    void create(RoleMember roleMember);

    void createAll(List<RoleMember> roleMembers);

    void delete(BigInteger id);

    void update(RoleMember roleMember);
}
