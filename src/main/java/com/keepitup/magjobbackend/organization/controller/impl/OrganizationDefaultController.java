package com.keepitup.magjobbackend.organization.controller.impl;

import com.keepitup.magjobbackend.member.entity.Member;
import com.keepitup.magjobbackend.member.service.api.MemberService;
import com.keepitup.magjobbackend.organization.controller.api.OrganizationController;
import com.keepitup.magjobbackend.organization.dto.*;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.function.*;
import com.keepitup.magjobbackend.organization.service.api.OrganizationService;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.service.api.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RestController
@Log
public class OrganizationDefaultController implements OrganizationController {
    private final OrganizationService service;
    private final OrganizationsToResponseFunction organizationsToResponse;
    private final OrganizationToResponseFunction organizationToResponse;
    private final RequestToOrganizationFunction requestToOrganization;
    private final UpdateOrganizationWithRequestFunction updateOrganizationWithRequest;
    private final MemberService memberService;
    private final UserService userService;


    @Autowired
    public OrganizationDefaultController(
            OrganizationService service,
            MemberService memberService,
            UserService userService,
            OrganizationsToResponseFunction organizationsToResponse,
            OrganizationToResponseFunction organizationToResponse,
            RequestToOrganizationFunction requestToOrganization,
            UpdateOrganizationWithRequestFunction updateOrganizationWithRequest
    ) {
        this.service = service;
        this.memberService = memberService;
        this.userService = userService;
        this.organizationsToResponse = organizationsToResponse;
        this.organizationToResponse = organizationToResponse;
        this.requestToOrganization = requestToOrganization;
        this.updateOrganizationWithRequest = updateOrganizationWithRequest;
    }

    @Override
    public GetOrganizationsResponse getOrganizations() {
        return organizationsToResponse.apply(service.findAll());
    }

    @Override
    public GetOrganizationResponse getOrganization(BigInteger id) {
        return service.find(id)
                .map(organizationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetOrganizationsResponse getOrganizationsByUser(BigInteger id) {
        Optional<List<Organization>> organizationsOptional = memberService.findAllOrganizationsByUser(id);

        List<Organization> organizations = organizationsOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return organizationsToResponse.apply(organizations);
    }

    @Override
    public GetOrganizationResponse createOrganization(PostOrganizationRequest postOrganizationRequest) {
        Optional<Organization> organization = service.findByName(postOrganizationRequest.getName());
        if (organization.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            service.create(requestToOrganization.apply(postOrganizationRequest));
            Optional<User> user = userService.find(postOrganizationRequest.getUser());
            Optional<Organization> createdOrganization = service.findByName(postOrganizationRequest.getName());

            if (user.isPresent() && createdOrganization.isPresent()) {
                memberService.create(Member.builder()
                        .pseudonym("Owner")
                        .organization(createdOrganization.get())
                        .user(user.get())
                        .build());
            }
        }
        return service.findByName(postOrganizationRequest.getName())
                .map(organizationToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteOrganization(BigInteger id) {
        service.find(id)
                .ifPresentOrElse(
                        organization -> service.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetOrganizationResponse updateOrganization(BigInteger id, PatchOrganizationRequest patchOrganizationRequest) {
        service.find(id)
                .ifPresentOrElse(
                        organization -> service.update(updateOrganizationWithRequest.apply(organization, patchOrganizationRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
        return getOrganization(id);
    }
}
