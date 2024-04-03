package com.keepitup.magjobbackend.rolemember.function;

import com.keepitup.magjobbackend.rolemember.dto.GetRoleMemberResponse;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RoleMemberToResponseFunction implements Function<RoleMember, GetRoleMemberResponse> {
    @Override
    public GetRoleMemberResponse apply(RoleMember roleMember) {
        return GetRoleMemberResponse.builder()
                .id(roleMember.getId())
                .member(GetRoleMemberResponse.Member.builder()
                        .id(roleMember.getMember().getId())
                        .pseudonym(roleMember.getMember().getPseudonym())
                        .build())
                .role(GetRoleMemberResponse.Role.builder()
                        .id(roleMember.getRole().getId())
                        .name(roleMember.getRole().getName())
                        .build())
                .build();
    }
}
