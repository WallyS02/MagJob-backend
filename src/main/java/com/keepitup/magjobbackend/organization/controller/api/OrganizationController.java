package com.keepitup.magjobbackend.organization.controller.api;

import com.keepitup.magjobbackend.organization.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

public interface OrganizationController {


    @GetMapping("api/organizations")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationsResponse getOrganizations();

    @GetMapping("api/organizations/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationsResponse getOrganizationsByUser(
            @PathVariable("userId")
            BigInteger id
    );

    @GetMapping("api/organizations/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationResponse getOrganization(
            @PathVariable("id")
            BigInteger id
    );

    @PostMapping("api/organizations")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetOrganizationResponse createOrganization(
            @RequestBody
            PostOrganizationRequest postOrganizationRequest
    );

    @PatchMapping("api/organizations/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetOrganizationResponse updateOrganization(
            @PathVariable("id")
            BigInteger id,
            @RequestBody
            PatchOrganizationRequest patchOrganizationRequest
    );

    @DeleteMapping("/api/organizations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteOrganization(
            @PathVariable("id")
            BigInteger id
    );
}
