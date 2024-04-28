package com.keepitup.magjobbackend.material.controller.api;

import com.keepitup.magjobbackend.material.dto.GetMaterialResponse;
import com.keepitup.magjobbackend.material.dto.GetMaterialsResponse;
import com.keepitup.magjobbackend.material.dto.PatchMaterialRequest;
import com.keepitup.magjobbackend.material.dto.PostMaterialRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name="Material Controller")
public interface MaterialController {
    @Operation(summary = "Get all Materials")
    @GetMapping("api/materials")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialsResponse getMaterials();

    @Operation(summary = "Get Material of given id")
    @GetMapping("api/materials/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialResponse getMaterial(
            @Parameter(
                    name = "id",
                    description = "Material id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Get Materials By Organization")
    @GetMapping("api/organizations/{organizationId}/materials")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialsResponse getMaterialsByOrganization(
            @Parameter(
                    name = "organizationId",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @Operation(summary = "Create Material")
    @PostMapping("api/materials")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetMaterialResponse createMaterial(
            @Parameter(
                    name = "PostMaterialRequest",
                    description = "PostMaterialRequest DTO",
                    schema = @Schema(implementation = PostMaterialRequest.class),
                    required = true
            )
            @RequestBody
            PostMaterialRequest postMaterialRequest
    );

    @Operation(summary = "Update Material")
    @PatchMapping("api/materials/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialResponse updateMaterial(
            @Parameter(
                    name = "id",
                    description = "Material id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id,
            @Parameter(
                    name = "PatchMaterialRequest",
                    description = "PatchMaterialRequest DTO",
                    schema = @Schema(implementation = PatchMaterialRequest.class),
                    required = true
            )
            @RequestBody
            PatchMaterialRequest patchMaterialRequest
    );

    @Operation(summary = "Delete Material")
    @DeleteMapping("/api/materials/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMaterial(
            @Parameter(
                    name = "id",
                    description = "Material id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );
}
