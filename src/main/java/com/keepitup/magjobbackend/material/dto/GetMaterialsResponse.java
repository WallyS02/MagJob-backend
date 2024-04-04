package com.keepitup.magjobbackend.material.dto;

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
@Schema(description = "GetMaterialsResponse DTO")
public class GetMaterialsResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Material {

        @Schema(description = "Material id value")
        private BigInteger id;

        @Schema(description = "Organization id value")
        private BigInteger organizationId;

        @Schema(description = "Material title")
        private String title;

        @Schema(description = "Material description")
        private String description;
    }

    @Singular
    @Schema(description = "Material list")
    private List<GetMaterialsResponse.Material> materials;
}
