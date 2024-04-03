package com.keepitup.magjobbackend.rolemember.function;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.role.entity.Role;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMemberRequest;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToRoleMemberFunction implements Function<PostRoleMemberRequest, RoleMember> {
    @Override
    public RoleMember apply(PostRoleMemberRequest request) {
        return RoleMember.builder()
                .member(Member.builder()
                        .id(request.getMember())
                        .build())
                .role(Role.builder()
                        .id(request.getRole())
                        .build())
                .build();
    }
}
