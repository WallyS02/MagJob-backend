package com.keepitup.magjobbackend.member.function;

import com.keepitup.magjobbackend.member.dto.PatchMemberRequest;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UpdateMemberWithRequestFunction implements BiFunction<Member, PatchMemberRequest, Member> {
    @Override
    public Member apply(Member member, PatchMemberRequest request) {
        return Member.builder()
                .id(member.getId())
                .pseudonym(request.getPseudonym())
                .isStillMember(member.getIsStillMember())
                .organization(member.getOrganization())
                .user(member.getUser())
                .build();
    }
}
