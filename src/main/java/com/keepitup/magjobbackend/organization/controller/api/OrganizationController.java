package com.keepitup.magjobbackend.organization.controller.api;

import com.keepitup.magjobbackend.organization.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.UUID;

@Tag(name="Organization Controller")
public interface OrganizationController {


    @Operation(summary = "Get all Organizations")
    @GetMapping("api/organizations")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationsResponse getOrganizations();

    @Operation(summary = "Get Organizations By User id")
    @GetMapping("api/organizations/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationsResponse getOrganizationsByUser(
            @Parameter(
                    name = "user id",
                    description = "User id value",
                    required = true
            )
            @PathVariable("id")
            UUID id
    );

    @Operation(summary = "Get Organization")
    @GetMapping("api/organizations/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationResponse getOrganization(
            @Parameter(
                    name = "id",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Create Organization")
    @PostMapping("api/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetOrganizationResponse createOrganization(
            @Parameter(
                    name = "PostOrganizationRequest",
                    description = "PostOrganizationRequest DTO",
                    schema = @Schema(implementation = PostOrganizationRequest.class),
                    required = true
            )
            @RequestBody
            PostOrganizationRequest postOrganizationRequest
    );

    @Operation(summary = "Update Organization")
    @PatchMapping("api/organizations/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationResponse updateOrganization(
            @Parameter(
                    name = "id",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id,
            @Parameter(
                    name = "PatchOrganizationRequest",
                    description = "PatchOrganizationRequest DTO",
                    schema = @Schema(implementation = PatchOrganizationRequest.class),
                    required = true
            )
            @RequestBody
            PatchOrganizationRequest patchOrganizationRequest
    );

    @Operation(summary = "Delete Organization")
    @DeleteMapping("/api/organizations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrganization(
            @Parameter(
                    name = "id",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );
}