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
@Schema(description = "AcceptInvitationRequest DTO")
public class AcceptInvitationRequest {

    @Schema(description = "Organization id value")
    private BigInteger organization;

    @Schema(description = "User external id value")
    private String user;

    @Schema(description = "Member pseudonym value")
    private String pseudonym;

}