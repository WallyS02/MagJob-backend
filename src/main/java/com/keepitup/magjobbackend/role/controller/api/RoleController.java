package com.keepitup.magjobbackend.role.controller.api;

import com.keepitup.magjobbackend.role.dto.GetRoleResponse;
import com.keepitup.magjobbackend.role.dto.GetRolesResponse;
import com.keepitup.magjobbackend.role.dto.PatchRoleRequest;
import com.keepitup.magjobbackend.role.dto.PostRoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name="Role Controller")
public interface RoleController {
    @Operation(summary = "Get all roles")
    @GetMapping("api/roles")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRolesResponse getRoles();

    @Operation(summary = "Get Role of given id")
    @GetMapping("api/roles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoleResponse getRole(
            @Parameter(
                    name = "id",
                    description = "Role id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Get Roles By Organization")
    @GetMapping("api/organizations/{organizationId}/roles")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRolesResponse getRolesByOrganization(
            @Parameter(
                    name = "organizationId",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @Operation(summary = "Create Role")
    @PostMapping("api/roles")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetRoleResponse createRole(
            @Parameter(
                    name = "PostRoleRequest",
                    description = "PostRoleRequest DTO",
                    schema = @Schema(implementation = PostRoleRequest.class),
                    required = true
            )
            @RequestBody
            PostRoleRequest postRoleRequest
    );

    @Operation(summary = "Update Role")
    @PostMapping("api/roles/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoleResponse updateRole(
            @Parameter(
                    name = "id",
                    description = "Role id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id,
            @Parameter(
                    name = "PatchRoleRequest",
                    description = "PatchRoleRequest DTO",
                    schema = @Schema(implementation = PatchRoleRequest.class),
                    required = true
            )
            @RequestBody
            PatchRoleRequest patchRoleRequest
    );

    @Operation(summary = "Delete Role")
    @DeleteMapping("/api/roles/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRole(
            @Parameter(
                    name = "id",
                    description = "Role id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );
}
