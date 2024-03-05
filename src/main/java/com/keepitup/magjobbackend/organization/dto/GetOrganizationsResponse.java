package com.keepitup.magjobbackend.organization.dto;

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
@Schema(description = "GetOrganizationsResponse DTO")
public class GetOrganizationsResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Organization {

        @Schema(description = "Organization id value")
        private BigInteger id;

        @Schema(description = "Organization name value")
        private String name;

    }

    @Singular
    @Schema(description = "Organization list")
    private List<Organization> organizations;

}