package com.keepitup.magjobbackend.role.controller.impl;

import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.impl.OrganizationDefaultService;
import com.keepitup.magjobbackend.role.controller.api.RoleController;
import com.keepitup.magjobbackend.role.dto.GetRoleResponse;
import com.keepitup.magjobbackend.role.dto.GetRolesResponse;
import com.keepitup.magjobbackend.role.dto.PatchRoleRequest;
import com.keepitup.magjobbackend.role.dto.PostRoleRequest;
import com.keepitup.magjobbackend.role.function.RequestToRoleFunction;
import com.keepitup.magjobbackend.role.function.RoleToResponseFunction;
import com.keepitup.magjobbackend.role.function.RolesToResponseFunction;
import com.keepitup.magjobbackend.role.function.UpdateRoleWithRequestFunction;
import com.keepitup.magjobbackend.role.service.impl.RoleDefaultService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@Controller
public class RoleDefaultController implements RoleController {
    private final RoleDefaultService roleService;
    private final OrganizationDefaultService organizationService;
    private final RoleToResponseFunction roleToResponseFunction;
    private final RolesToResponseFunction rolesToResponseFunction;
    private final RequestToRoleFunction requestToRoleFunction;
    private final UpdateRoleWithRequestFunction updateRoleWithRequestFunction;

    @Autowired
    public RoleDefaultController(
            RoleDefaultService roleService,
            OrganizationDefaultService organizationService,
            RoleToResponseFunction roleToResponseFunction,
            RolesToResponseFunction rolesToResponseFunction,
            RequestToRoleFunction requestToRoleFunction,
            UpdateRoleWithRequestFunction updateRoleWithRequestFunction
    ) {
        this.roleService = roleService;
        this.organizationService = organizationService;
        this.roleToResponseFunction = roleToResponseFunction;
        this.rolesToResponseFunction = rolesToResponseFunction;
        this.requestToRoleFunction = requestToRoleFunction;
        this.updateRoleWithRequestFunction = updateRoleWithRequestFunction;
    }

    @Override
    public GetRolesResponse getRoles() {
        return rolesToResponseFunction.apply(roleService.findAll());
    }

    @Override
    public GetRoleResponse getRole(BigInteger id) {
        return roleService.find(id)
                .map(roleToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetRolesResponse getRolesByOrganization(BigInteger organizationId) {
        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return rolesToResponseFunction.apply(roleService.findAllByOrganization(organization));
    }

    @Override
    public GetRoleResponse createRole(PostRoleRequest postRoleRequest) {
        roleService.create(requestToRoleFunction.apply(postRoleRequest));

        return roleService.findByName(postRoleRequest.getName())
                .map(roleToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetRoleResponse updateRole(BigInteger id, PatchRoleRequest patchRoleRequest) {
        roleService.find(id)
                .ifPresentOrElse(
                        role -> roleService.update(updateRoleWithRequestFunction.apply(role, patchRoleRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );

        return getRole(id);
    }

    @Override
    public void deleteRole(BigInteger id) {
        roleService.find(id)
                .ifPresentOrElse(
                        role -> roleService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
