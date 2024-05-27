package com.keepitup.magjobbackend.member.service.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.repository.api.MemberRepository;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.repository.api.OrganizationRepository;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.repository.api.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

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
    public Page<Member> findAllByIsStillMember(Boolean isStillMember, Pageable pageable) {
        return memberRepository.findAllByIsStillMember(isStillMember, pageable);
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
    public Page<Member> findAllByPseudonym(String pseudonym, Pageable pageable) {
        return memberRepository.findAllByPseudonym(pseudonym, pageable);
    }

    @Override
    public Page<Member> findAllByOrganization(Organization organization, Pageable pageable) {
        return memberRepository.findAllByOrganization(organization, pageable);
    }

    @Override
    public Page<Member> findAllByOrganizationAndIsStillMember(Organization organization, Boolean isStillMember, Pageable pageable) {
        return memberRepository.findAllByOrganizationAndIsStillMember(organization, isStillMember, pageable);
    }

    @Override
    public Boolean checkIfStillMember(BigInteger id) {
        Optional<Member> member = memberRepository.findById(id);
        return member.map(Member::getIsStillMember).orElse(null);
    }

    @Override
    public Optional<Page<User>> findAllUsersByOrganization(BigInteger organizationId, Pageable pageable) {
        return organizationRepository.findById(organizationId)
                .map(organization -> memberRepository.findAllByOrganization(organization, pageable))
                .map(members -> members.map(Member::getUser));
    }

    @Override
    public Optional<Page<Organization>> findAllOrganizationsByUser(BigInteger userId, Pageable pageable) {
        return userRepository.findById(userId)
                .map(user -> memberRepository.findAllByUser(user, pageable))
                .map(members -> members.map(Member::getOrganization));
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
