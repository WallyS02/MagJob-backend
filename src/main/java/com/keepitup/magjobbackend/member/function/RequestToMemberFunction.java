package com.keepitup.magjobbackend.member.function;

import com.keepitup.magjobbackend.member.dto.PostMemberRequest;
import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToMemberFunction implements Function<PostMemberRequest, Member> {

    @Override
    public Member apply(PostMemberRequest request) {
        return Member.builder()
                .pseudonym(request.getPseudonym())
                .user(User.builder()
                        .id(request.getUserId())
                        .build())
                .organization(Organization.builder()
                        .id(request.getOrganization())
                        .build())
                .build();
    }
}
