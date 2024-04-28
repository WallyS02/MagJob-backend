package com.keepitup.magjobbackend.material.controller.impl;

import com.keepitup.magjobbackend.material.controller.api.MaterialController;
import com.keepitup.magjobbackend.material.dto.GetMaterialResponse;
import com.keepitup.magjobbackend.material.dto.GetMaterialsResponse;
import com.keepitup.magjobbackend.material.dto.PatchMaterialRequest;
import com.keepitup.magjobbackend.material.dto.PostMaterialRequest;
import com.keepitup.magjobbackend.material.function.MaterialToResponseFunction;
import com.keepitup.magjobbackend.material.function.MaterialsToResponseFunction;
import com.keepitup.magjobbackend.material.function.RequestToMaterialFunction;
import com.keepitup.magjobbackend.material.function.UpdateMaterialWithRequestFunction;
import com.keepitup.magjobbackend.material.service.impl.MaterialDefaultService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.impl.OrganizationDefaultService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@RestController
@Log
public class MaterialDefaultController implements MaterialController {
    private final MaterialDefaultService materialService;
    private final OrganizationDefaultService organizationService;
    private final MaterialToResponseFunction materialToResponseFunction;
    private final MaterialsToResponseFunction materialsToResponseFunction;
    private final RequestToMaterialFunction requestToMaterialFunction;
    private final UpdateMaterialWithRequestFunction updateMaterialWithRequestFunction;

    @Autowired
    public MaterialDefaultController(
            MaterialDefaultService materialService,
            OrganizationDefaultService organizationService,
            MaterialToResponseFunction materialToResponseFunction,
            MaterialsToResponseFunction materialsToResponseFunction,
            RequestToMaterialFunction requestToMaterialFunction,
            UpdateMaterialWithRequestFunction updateMaterialWithRequestFunction
    ) {
        this.materialService = materialService;
        this.organizationService = organizationService;
        this.materialToResponseFunction = materialToResponseFunction;
        this.materialsToResponseFunction = materialsToResponseFunction;
        this.requestToMaterialFunction = requestToMaterialFunction;
        this.updateMaterialWithRequestFunction = updateMaterialWithRequestFunction;
    }

    @Override
    public GetMaterialsResponse getMaterials() {
        return materialsToResponseFunction.apply(materialService.findAll());
    }

    @Override
    public GetMaterialResponse getMaterial(BigInteger id) {
        return materialService.find(id)
                .map(materialToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMaterialsResponse getMaterialsByOrganization(BigInteger organizationId) {
        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return materialsToResponseFunction.apply(materialService.findAllByOrganization(organization));
    }

    @Override
    public GetMaterialResponse createMaterial(PostMaterialRequest postMaterialRequest) {
        materialService.create(requestToMaterialFunction.apply(postMaterialRequest));
        return materialService.findByTitle(postMaterialRequest.getTitle())
                .map(materialToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMaterialResponse updateMaterial(BigInteger id, PatchMaterialRequest patchMaterialRequest) {
        materialService.find(id)
                .ifPresentOrElse(
                        material -> materialService.update(updateMaterialWithRequestFunction.apply(material, patchMaterialRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
        return getMaterial(id);
    }

    @Override
    public void deleteMaterial(BigInteger id) {
        materialService.find(id)
                .ifPresentOrElse(
                        material -> materialService.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
