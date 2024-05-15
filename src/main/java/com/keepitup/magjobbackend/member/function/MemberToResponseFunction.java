package com.keepitup.magjobbackend.member.function;

import com.keepitup.magjobbackend.member.dto.GetMemberResponse;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MemberToResponseFunction implements Function<Member, GetMemberResponse> {
    @Override
    public GetMemberResponse apply(Member member) {
        return GetMemberResponse.builder()
                .id(member.getId())
                .pseudonym(member.getPseudonym())
                .isStillMember(member.getIsStillMember())
                .user(GetMemberResponse.User.builder()
                        .id(member.getUser().getId())
                        .email(member.getUser().getEmail())
                        .build())
                .organization(GetMemberResponse.Organization.builder()
                        .id(member.getOrganization().getId())
                        .name(member.getOrganization().getName())
                        .build())
                .build();
    }
}
