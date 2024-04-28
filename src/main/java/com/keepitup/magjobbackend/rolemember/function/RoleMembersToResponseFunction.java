package com.keepitup.magjobbackend.rolemember.function;

import com.keepitup.magjobbackend.rolemember.dto.GetRoleMembersResponse;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class RoleMembersToResponseFunction implements Function<List<RoleMember>, GetRoleMembersResponse> {
    @Override
    public GetRoleMembersResponse apply(List<RoleMember> roleMembers) {
        return GetRoleMembersResponse.builder()
                .roleMembers(roleMembers.stream()
                        .map(roleMember -> GetRoleMembersResponse.RoleMember.builder()
                                .id(roleMember.getId())
                                .memberId(roleMember.getMember().getId())
                                .roleId(roleMember.getRole().getId())
                                .build())
                        .toList())
                .build();
    }
}
