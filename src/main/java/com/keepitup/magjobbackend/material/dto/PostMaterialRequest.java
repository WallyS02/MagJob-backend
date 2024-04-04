package com.keepitup.magjobbackend.material.dto;

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
@Schema(description = "PostMaterialRequest DTO")
public class PostMaterialRequest {
    @Schema(description = "Material title")
    private String title;

    @Schema(description = "Material description")
    private String description;

    @Schema(description = "Material content type")
    private String contentType;

    @Schema(description = "Material size")
    private Long size;

    @Schema(description = "Material content")
    private byte[] content;

    @Schema(description = "Material organization id value")
    private BigInteger organization;
}
