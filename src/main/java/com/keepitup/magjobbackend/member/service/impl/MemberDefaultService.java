package com.keepitup.magjobbackend.member.service.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberDefaultService implements MemberService {
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public MemberDefaultService(MemberRepository memberRepository, UserRepository userRepository, OrganizationRepository organizationRepository) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public List<Member> findAllByIsStillMember(Boolean isStillMember) {
        return memberRepository.findAllByIsStillMember(isStillMember);
    }

    @Override
    public Optional<Member> find(BigInteger id) {
        return memberRepository.findById(id);
    }

    @Override
    public Optional<Member> findByIdAndIsStillMember(BigInteger id, Boolean isStillMember) {
        return memberRepository.findByIdAndIsStillMember(id, isStillMember);
    }

    @Override
    public List<Member> findAllByPseudonym(String pseudonym) {
        return memberRepository.findAllByPseudonym(pseudonym);
    }

    @Override
    public List<Member> findAllByOrganization(Organization organization) {
        return memberRepository.findAllByOrganization(organization);
    }

    @Override
    public List<Member> findAllByOrganizationAndIsStillMember(Organization organization, Boolean isStillMember) {
        return memberRepository.findAllByOrganizationAndIsStillMember(organization, isStillMember);
    }

    @Override
    public Boolean checkIfStillMember(BigInteger id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(Member::getIsStillMember).orElse(null);
    }

    @Override
    public Optional<List<User>> findAllUsersByOrganization(BigInteger organizationId) {
        return organizationRepository.findById(organizationId)
                .map(memberRepository::findAllByOrganization)
                .map(members -> members.stream()
                        .map(Member::getUser)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Organization>> findAllOrganizationsByUser(UUID userId) {
        return userRepository.findById(userId)
                .map(memberRepository::findAllByUser)
                .map(members -> members.stream()
                        .map(Member::getOrganization)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<List<Organization>> findAllOrganizationsByUserId(UUID userId) {
        return userRepository.findById(userId)
                .map(memberRepository::findAllByUser)
                .map(members -> members.stream()
                        .map(Member::getOrganization)
                        .collect(Collectors.toList()));
    }

    @Override
    public Optional<Member> findByUserAndOrganization(User user, Organization organization) {
        return memberRepository.findByUserAndOrganization(user, organization);
    }

    @Override
    public void create(Member member) {
        member.setIsStillMember(true);
        memberRepository.save(member);
    }

    @Override
    public void delete(BigInteger id) {
        memberRepository.findById(id).ifPresent(member -> member.setIsStillMember(false));
    }

    @Override
    public void update(Member member) {
        memberRepository.save(member);
    }
}
