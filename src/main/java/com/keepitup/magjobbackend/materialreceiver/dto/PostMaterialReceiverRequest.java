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
@Schema(description = "PostMaterialReceiverRequest DTO")
public class PostMaterialReceiverRequest {
    @Schema(description = "Member id value")
    private BigInteger member;

    @Schema(description = "Material id value")
    private BigInteger material;
}
