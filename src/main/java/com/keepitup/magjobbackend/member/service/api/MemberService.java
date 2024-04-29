package com.keepitup.magjobbackend.member.service.api;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;

import javax.swing.text.html.Option;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<Member> findAllByIsStillMember(Boolean isStillMember);
    Optional<Member> find(BigInteger id);

    Optional<Member> findByIdAndIsStillMember(BigInteger id, Boolean isStillMember);

    List<Member> findAllByPseudonym(String pseudonym);
    List<Member> findAllByOrganization(Organization organization);
    List<Member> findAllByOrganizationAndIsStillMember(Organization organization, Boolean isStillMember);

    Boolean checkIfStillMember(BigInteger id);

    Optional<List<User>> findAllUsersByOrganization(BigInteger organizationId);

    Optional<List<Organization>> findAllOrganizationsByUser(BigInteger userId);
    Optional<List<Organization>> findAllOrganizationsByUserExternalId(String externalId);

    Optional<Member> findByUserAndOrganization(User user, Organization organization);

    void create(Member member);

    void delete(BigInteger id);

    void update(Member member);
}
