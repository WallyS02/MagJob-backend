package com.keepitup.magjobbackend.rolemember.service.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import com.keepitup.magjobbackend.rolemember.repository.api.RoleMemberRepository;
import com.keepitup.magjobbackend.rolemember.service.api.RoleMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class RoleMemberDefaultService implements RoleMemberService {
    private final RoleMemberRepository roleMemberRepository;

    @Autowired
    public RoleMemberDefaultService(RoleMemberRepository roleMemberRepository) {
        this.roleMemberRepository = roleMemberRepository;
    }

    @Override
    public Optional<RoleMember> find(BigInteger id) {
        return roleMemberRepository.findById(id);
    }

    @Override
    public Optional<RoleMember> findByMemberAndRole(Member member, Role role) {
        return roleMemberRepository.findByMemberAndRole(member, role);
    }

    @Override
    public List<RoleMember> findAll() {
        return roleMemberRepository.findAll();
    }

    @Override
    public List<RoleMember> findAllByMember(Member member) {
        return roleMemberRepository.findAllByMember(member);
    }

    @Override
    public List<RoleMember> findAllByRole(Role role) {
        return roleMemberRepository.findAllByRole(role);
    }

    @Override
    public void create(RoleMember roleMember) {
        roleMemberRepository.save(roleMember);
    }

    @Override
    public void delete(BigInteger id) {
        roleMemberRepository.findById(id).ifPresent(roleMemberRepository::delete);
    }

    @Override
    public void update(RoleMember roleMember) {
        roleMemberRepository.save(roleMember);
    }
}
