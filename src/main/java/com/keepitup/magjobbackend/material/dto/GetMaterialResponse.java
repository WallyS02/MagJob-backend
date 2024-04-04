package com.keepitup.magjobbackend.material.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetMaterialResponse DTO")
public class GetMaterialResponse {
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

        @Schema(description = "Organization name")
        private String name;

    }

    @Schema(description = "Material id value")
    private BigInteger id;

    @Schema(description = "Material title")
    private String title;

    @Schema(description = "Material description")
    private String description;

    @Schema(description = "Material content type")
    private String contentType;

    @Schema(description = "Material size")
    private Long size;

    @Schema(description = "Material date of creation")
    private ZonedDateTime dateOfCreation;

    @Schema(description = "Material content")
    private byte[] content;

    @Schema(description = "Material organization")
    private GetMaterialResponse.Organization organization;
}
