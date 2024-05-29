package com.keepitup.magjobbackend.materialreceiver.dto;

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
@Schema(description = "GetMaterialReceiversResponse DTO")
public class GetMaterialReceiversResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class MaterialReceiver {

        @Schema(description = "Material receiver id value")
        private BigInteger id;

        @Schema(description = "Member id value")
        private BigInteger memberId;

        @Schema(description = "Material id value")
        private BigInteger materialId;
    }

    @Singular
    @Schema(description = "Material receiver list")
    private List<GetMaterialReceiversResponse.MaterialReceiver> materialReceivers;

    @Schema(description = "Number of all objects")
    private Integer count;
}
