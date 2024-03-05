package com.keepitup.magjobbackend.member.function;

import com.keepitup.magjobbackend.member.dto.GetMembersResponse;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.List;

@Component
public class MembersToResponseFunction implements Function<List<Member>, GetMembersResponse> {
    @Override
    public GetMembersResponse apply(List<Member> entities) {
        return GetMembersResponse.builder()
                .members(entities.stream()
                        .map(member -> GetMembersResponse.Member.builder()
                                .id(member.getId())
                                .userId(member.getUser().getId())
                                .pseudonym(member.getPseudonym())
                                .firstName(member.getUser().getFirstname())
                                .lastName(member.getUser().getLastname())
                                .build())
                        .toList())
                .build();
    }
}
