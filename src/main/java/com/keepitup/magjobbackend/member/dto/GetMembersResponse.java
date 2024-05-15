package com.keepitup.magjobbackend.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetMembersResponse DTO")
public class GetMembersResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        @Schema(description = "Member id value")
        private BigInteger id;

        @Schema(description = "User id value")
        private UUID userId;

        @Schema(description = "Member pseudonym value")
        private String pseudonym;

        @Schema(description = "User firstname value")
        private String firstName;

        @Schema(description = "User lastname value")
        private String lastName;

    }

    @Singular
    @Schema(description = "Member list")
    private List<Member> members;

}