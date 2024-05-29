package com.keepitup.magjobbackend.rolemember.function;

import com.keepitup.magjobbackend.rolemember.dto.GetRoleMembersResponse;
import com.keepitup.magjobbackend.rolemember.entity.RoleMember;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class RoleMembersToResponseFunction implements BiFunction<Page<RoleMember>, Integer, GetRoleMembersResponse> {
    @Override
    public GetRoleMembersResponse apply(Page<RoleMember> roleMembers, Integer count) {
        return GetRoleMembersResponse.builder()
                .roleMembers(roleMembers.stream()
                        .map(roleMember -> GetRoleMembersResponse.RoleMember.builder()
                                .id(roleMember.getId())
                                .memberId(roleMember.getMember().getId())
                                .roleId(roleMember.getRole().getId())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
