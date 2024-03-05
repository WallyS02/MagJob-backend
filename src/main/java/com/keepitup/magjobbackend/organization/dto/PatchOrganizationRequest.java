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
@Schema(description = "PatchOrganizationRequest DTO")
public class PatchOrganizationRequest {

    @Schema(description = "Organization name value")
    private String name;

    @Schema(description = "Organization profile banner url value")
    private String profileBannerUrl;

}