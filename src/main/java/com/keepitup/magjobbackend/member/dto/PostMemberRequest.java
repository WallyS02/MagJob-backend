package com.keepitup.magjobbackend.member.dto;

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
@Schema(description = "PostMemberRequest DTO")
public class PostMemberRequest {

    @Schema(description = "Member pseudonym value")
    private String pseudonym;

    @Schema(description = "Organization id value")
    private BigInteger organization;

    @Schema(description = "User id value")
    private BigInteger user;

}