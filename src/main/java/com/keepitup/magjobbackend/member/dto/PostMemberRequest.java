package com.keepitup.magjobbackend.member.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostMemberRequest {

    private String pseudonym;

    private BigInteger organization;
    private BigInteger user;

}
