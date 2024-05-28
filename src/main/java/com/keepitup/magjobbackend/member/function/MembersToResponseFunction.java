package com.keepitup.magjobbackend.member.function;

import com.keepitup.magjobbackend.member.dto.GetMembersResponse;
import com.keepitup.magjobbackend.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class MembersToResponseFunction implements BiFunction<Page<Member>, Integer, GetMembersResponse> {
    @Override
    public GetMembersResponse apply(Page<Member> entities, Integer count) {
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
                .count(count)
                .build();
    }
}
