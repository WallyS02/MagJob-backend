package com.keepitup.magjobbackend.rolemember.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PostRoleMemberRequest DTO")
public class PostRoleMemberRequest {
    @Schema(description = "Member id value")
    private BigInteger member;

    @Schema(description = "Role id value")
    private BigInteger role;
}
