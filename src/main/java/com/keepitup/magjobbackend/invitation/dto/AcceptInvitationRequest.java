package com.keepitup.magjobbackend.invitation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.util.UUID;

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

    @Schema(description = "User id value")
    private UUID userId;

    @Schema(description = "Member pseudonym value")
    private String pseudonym;

}