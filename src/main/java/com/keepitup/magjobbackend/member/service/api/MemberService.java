package com.keepitup.magjobbackend.member.service.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;
import java.util.Optional;

public interface MemberService {
    Page<Member> findAllByIsStillMember(Boolean isStillMember, Pageable pageable);

    Optional<Member> find(BigInteger id);

    Optional<Member> findByIdAndIsStillMember(BigInteger id, Boolean isStillMember);

    Page<Member> findAllByPseudonym(String pseudonym, Pageable pageable);

    Page<Member> findAllByOrganization(Organization organization, Pageable pageable);

    Page<Member> findAllByOrganizationAndIsStillMember(Organization organization, Boolean isStillMember, Pageable pageable);

    Boolean checkIfStillMember(BigInteger id);

    Optional<Page<User>> findAllUsersByOrganization(BigInteger organizationId, Pageable pageable);

    Optional<Page<Organization>> findAllOrganizationsByUser(BigInteger userId, Pageable pageable);

    Optional<Member> findByUserAndOrganization(User user, Organization organization);

    void create(Member member);

    void delete(BigInteger id);

    void update(Member member);
}
