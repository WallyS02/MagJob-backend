package com.keepitup.magjobbackend.member.repository.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, BigInteger> {
    List<Member> findAllByIsStillMember(Boolean isStillMember);
    Optional<Member> findByIdAndIsStillMember(BigInteger id, Boolean isStillMember);
    List<Member> findAllByPseudonym(String pseudonym);
    List<Member> findAllByOrganization(Organization organization);
    List<Member> findAllByOrganizationAndIsStillMember(Organization organization, Boolean isStillMember);
    List<Member> findAllByUser(User user);
    Optional<Member> findByUserAndOrganization(User user, Organization organization);
}
