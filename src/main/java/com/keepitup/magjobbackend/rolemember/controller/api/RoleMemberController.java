package com.keepitup.magjobbackend.rolemember.controller.api;

import com.keepitup.magjobbackend.configuration.PageConfig;
import com.keepitup.magjobbackend.rolemember.dto.GetRoleMemberResponse;
import com.keepitup.magjobbackend.rolemember.dto.GetRoleMembersResponse;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMemberRequest;
import com.keepitup.magjobbackend.rolemember.dto.PostRoleMembersRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.UUID;

@Tag(name="Role Member Controller")
public interface RoleMemberController {
    PageConfig pageConfig = new PageConfig();

    @Operation(summary = "Get all Role Members")
    @GetMapping("api/role-members")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoleMembersResponse getRoleMembers(
            @Parameter(
                    name = "page number",
                    description = "Page number to retrieve"
            )
            @RequestParam(defaultValue = "#{pageConfig.number}")
            int page,
            @Parameter(
                    name = "page size",
                    description = "Number of records per page"
            )
            @RequestParam(defaultValue = "#{pageConfig.size}")
            int size
    );

    @Operation(summary = "Get Role Member of given id")
    @GetMapping("api/role-members/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoleMemberResponse getRoleMember(
            @Parameter(
                    name = "id",
                    description = "Role Member id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Get Role Members By Role")
    @GetMapping("api/roles/{roleId}/role-members")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoleMembersResponse getRoleMembersByRole(
            @Parameter(
                    name = "page number",
                    description = "Page number to retrieve"
            )
            @RequestParam(defaultValue = "#{pageConfig.number}")
            int page,
            @Parameter(
                    name = "page size",
                    description = "Number of records per page"
            )
            @RequestParam(defaultValue = "#{pageConfig.size}")
            int size,
            @Parameter(
                    name = "roleId",
                    description = "Role id value",
                    required = true
            )
            @PathVariable("roleId")
            BigInteger roleId
    );

    @Operation(summary = "Get Role Members By Member")
    @GetMapping("api/members/{memberId}/role-members")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetRoleMembersResponse getRoleMembersByMember(
            @Parameter(
                    name = "page number",
                    description = "Page number to retrieve"
            )
            @RequestParam(defaultValue = "#{pageConfig.number}")
            int page,
            @Parameter(
                    name = "page size",
                    description = "Number of records per page"
            )
            @RequestParam(defaultValue = "#{pageConfig.size}")
            int size,
            @Parameter(
                    name = "memberId",
                    description = "Member id value",
                    required = true
            )
            @PathVariable("memberId")
            BigInteger memberId
    );

    @Operation(summary = "Create Role Member")
    @PostMapping("api/role-members")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetRoleMemberResponse createRoleMember(
            @Parameter(
                    name = "PostRoleMemberRequest",
                    description = "PostRoleMemberRequest DTO",
                    schema = @Schema(implementation = PostRoleMemberRequest.class),
                    required = true
            )
            @RequestBody
            PostRoleMemberRequest postRoleMemberRequest
    );

    @Operation(summary = "Create Many Role Members")
    @PostMapping("api/role-members/list")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetRoleMembersResponse createRoleMembers(
            @Parameter(
                    name = "PostRoleMembersRequest",
                    description = "PostRoleMembersRequest DTO",
                    schema = @Schema(implementation = PostRoleMembersRequest.class),
                    required = true
            )
            @RequestBody
            PostRoleMembersRequest postRoleMembersRequest
    );



    @Operation(summary = "Delete Role Member")
    @DeleteMapping("/api/role-members/{memberId}/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteRoleMember(
            @Parameter(
                    name = "memberId",
                    description = "Member id value",
                    required = true
            )
            @PathVariable("memberId")
            BigInteger memberId,
            @Parameter(
                    name = "roleId",
                    description = "Role id value",
                    required = true
            )
            @PathVariable("roleId")
            BigInteger roleId
    );
}
