package com.keepitup.magjobbackend.invitation.dto;

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
@Schema(description = "PostInvitationRequest DTO")
public class PostInvitationRequest {

    @Schema(description = "Organization id value")
    private BigInteger organization;

    @Schema(description = "User id value")
    private BigInteger user;

}