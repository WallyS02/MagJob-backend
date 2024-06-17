package com.keepitup.magjobbackend.materialreceiver.dto;

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
@Schema(description = "GetMaterialReceiverResponse DTO")
public class GetMaterialReceiverResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        @Schema(description = "Member id value")
        private BigInteger id;

        @Schema(description = "Member pseudonym")
        private String pseudonym;

    }

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

        @Schema(description = "Material title")
        private String title;

    }

    @Schema(description = "Material receiver id value")
    private BigInteger id;

    @Schema(description = "Member class value")
    private GetMaterialReceiverResponse.Member member;

    @Schema(description = "Material class value")
    private GetMaterialReceiverResponse.Material material;
}
