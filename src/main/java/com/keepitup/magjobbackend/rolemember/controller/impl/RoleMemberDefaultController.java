package com.keepitup.magjobbackend.rolemember.controller.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.impl.MemberDefaultService;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.role.service.impl.RoleDefaultService;
import com.keepitup.magjobbackend.rolemember.controller.api.RoleMemberController;
import com.keepitup.magjobbackend.rolemember.dto.GetRoleMemberResponse;
import com.keepitup.magjobbackend.rolemember.dto.GetRoleMembersResponse;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMemberRequest;
import com.keepitup.magjobbackend.rolemember.function.RequestToRoleMemberFunction;
import com.keepitup.magjobbackend.rolemember.function.RoleMemberToResponseFunction;
import com.keepitup.magjobbackend.rolemember.function.RoleMembersToResponseFunction;
import com.keepitup.magjobbackend.rolemember.service.impl.RoleMemberDefaultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@Controller
public class RoleMemberDefaultController implements RoleMemberController {
    private final RoleMemberDefaultService roleMemberService;
    private final RoleDefaultService roleService;
    private final MemberDefaultService memberService;
    private final RoleMemberToResponseFunction roleMemberToResponseFunction;
    private final RoleMembersToResponseFunction roleMembersToResponseFunction;
    private final RequestToRoleMemberFunction requestToRoleMemberFunction;

    @Autowired
    public RoleMemberDefaultController(
            RoleMemberDefaultService roleMemberService,
            RoleDefaultService roleService,
            MemberDefaultService memberService,
            RoleMemberToResponseFunction roleMemberToResponseFunction,
            RoleMembersToResponseFunction roleMembersToResponseFunction,
            RequestToRoleMemberFunction requestToRoleMemberFunction
    ) {
        this.roleMemberService = roleMemberService;
        this.roleService = roleService;
        this.memberService = memberService;
        this.roleMemberToResponseFunction = roleMemberToResponseFunction;
        this.roleMembersToResponseFunction = roleMembersToResponseFunction;
        this.requestToRoleMemberFunction = requestToRoleMemberFunction;
    }

    @Override
    public GetRoleMembersResponse getRoleMembers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = roleMemberService.findAll().size();
        return roleMembersToResponseFunction.apply(roleMemberService.findAll(pageRequest), count);
    }

    @Override
    public GetRoleMemberResponse getRoleMember(BigInteger id) {
        return roleMemberService.find(id)
                .map(roleMemberToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetRoleMembersResponse getRoleMembersByRole(int page, int size, BigInteger roleId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Role> roleOptional = roleService.find(roleId);

        Role role = roleOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Integer count = roleMemberService.findAllByRole(role, Pageable.unpaged()).getNumberOfElements();

        return roleMembersToResponseFunction.apply(roleMemberService.findAllByRole(role, pageRequest), count);
    }

    @Override
    public GetRoleMembersResponse getRoleMembersByMember(int page, int size, BigInteger memberId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Member> memberOptional = memberService.find(memberId);

        Member member = memberOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Integer count = roleMemberService.findAllByMember(member, Pageable.unpaged()).getNumberOfElements();

        return roleMembersToResponseFunction.apply(roleMemberService.findAllByMember(member, pageRequest), count);
    }

    @Override
    public GetRoleMemberResponse createRoleMember(PostRoleMemberRequest postRoleMemberRequest) {
        roleMemberService.create(requestToRoleMemberFunction.apply(postRoleMemberRequest));

        return roleMemberService.findByMemberAndRole(
                    memberService.find(postRoleMemberRequest.getMember()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                    ),
                    roleService.find(postRoleMemberRequest.getRole()).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                    ))
                .map(roleMemberToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteRoleMember(BigInteger id) {
        roleMemberService.find(id)
                .ifPresentOrElse(
                        roleMember -> roleMemberService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
