package com.keepitup.magjobbackend.member.repository.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, BigInteger> {
    Page<Member> findAllByIsStillMember(Boolean isStillMember, Pageable pageable);
    Optional<Member> findByIdAndIsStillMember(BigInteger id, Boolean isStillMember);
    Page<Member> findAllByPseudonym(String pseudonym, Pageable pageable);
    Page<Member> findAllByOrganization(Organization organization, Pageable pageable);
    Page<Member> findAllByOrganizationAndIsStillMember(Organization organization, Boolean isStillMember, Pageable pageable);
    Page<Member> findAllByUser(User user, Pageable pageable);
    Optional<Member> findByUserAndOrganization(User user, Organization organization);
}
