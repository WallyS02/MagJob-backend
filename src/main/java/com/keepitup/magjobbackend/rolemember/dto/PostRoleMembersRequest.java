package com.keepitup.magjobbackend.rolemember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "PostRoleMemberRequest DTO")
public class PostRoleMembersRequest {
    @Schema(description = "Role id value")
    private BigInteger roleId;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class RoleMember {
        @Schema(description = "Member id value")
        private BigInteger memberId;
    }

    @Singular
    @Schema(description = "Role member list")
    private List<RoleMember> roleMembers;
}
