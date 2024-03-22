package com.keepitup.magjobbackend.user.controller.api;

import com.keepitup.magjobbackend.user.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigInteger;

@Tag(name = "User Controller")
public interface UserController {


    @Operation(summary = "Get all Users")
    @GetMapping("api/users")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetUsersResponse getUsers();

    @Operation(summary = "Get User")
    @GetMapping("/api/users/{externalId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetUserResponse getUser(
            @Parameter(
                    name = "external id",
                    description = "User external id value",
                    required = true
            )
            @PathVariable("externalId")
            String externalId
    );

    @Operation(summary = "Create User")
    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetUserResponse createUser(
            @RequestHeader String token
    );

    @Operation(summary = "Delete User")
    @DeleteMapping("/api/users/{externalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(
            @Parameter(
                    name = "externalId",
                    description = "User external id value",
                    required = true
            )
            @PathVariable("externalId")
            String externalId
    );

    @Operation(summary = "Update User")
    @PatchMapping("/api/users/{externalId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetUserResponse updateUser(
            @Parameter(
                    name = "externalId",
                    description = "User external id value",
                    required = true
            )
            @PathVariable("externalId")
            String externalId,
            @Parameter(
                    name = "PatchUserRequest",
                    description = "PatchUserRequest DTO",
                    schema = @Schema(implementation = PatchUserRequest.class),
                    required = true
            )
            @RequestBody
            PatchUserRequest patchUserRequest
    );
}
