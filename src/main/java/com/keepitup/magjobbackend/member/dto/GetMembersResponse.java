package com.keepitup.magjobbackend.member.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetMembersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        private BigInteger id;
        private BigInteger userId;

        private String pseudonym;
        private String firstName;
        private String lastName;

    }

    @Singular
    private List<Member> members;

}
