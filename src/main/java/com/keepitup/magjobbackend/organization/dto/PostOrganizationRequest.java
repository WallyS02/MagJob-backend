package com.keepitup.magjobbackend.organization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PostOrganizationRequest DTO")
public class PostOrganizationRequest {

    @Schema(description = "Organization name value")
    private String name;

    @Schema(description = "Organization profile banner url value")
    private String profileBannerUrl;

    @Schema(description = "User external id value")
    private String userExternalId;

}