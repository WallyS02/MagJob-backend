package com.keepitup.magjobbackend.material.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PatchMaterialRequest DTO")
public class PatchMaterialRequest {
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
}
