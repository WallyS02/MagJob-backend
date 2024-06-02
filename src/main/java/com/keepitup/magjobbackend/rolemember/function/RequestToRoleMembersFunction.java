package com.keepitup.magjobbackend.rolemember.function;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMembersRequest;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RequestToRoleMembersFunction implements Function<PostRoleMembersRequest, List<RoleMember>> {

    @Override
    public List<RoleMember> apply(PostRoleMembersRequest postRoleMembersRequest) {
        return postRoleMembersRequest.getRoleMembers().stream()
                .map(roleMember -> RoleMember.builder()
                        .member(Member.builder()
                                .id(roleMember.getMemberId())
                                .build())
                        .role(Role.builder()
                                .id(postRoleMembersRequest.getRoleId())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }
}