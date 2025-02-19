package com.keepitup.magjobbackend.material.controller.impl;

import com.keepitup.magjobbackend.material.controller.api.MaterialController;
import com.keepitup.magjobbackend.material.dto.GetMaterialResponse;
import com.keepitup.magjobbackend.material.dto.GetMaterialsResponse;
import com.keepitup.magjobbackend.material.dto.PatchMaterialRequest;
import com.keepitup.magjobbackend.material.dto.PostMaterialRequest;
import com.keepitup.magjobbackend.material.entity.Material;
import com.keepitup.magjobbackend.material.function.MaterialToResponseFunction;
import com.keepitup.magjobbackend.material.function.MaterialsToResponseFunction;
import com.keepitup.magjobbackend.material.function.RequestToMaterialFunction;
import com.keepitup.magjobbackend.material.function.UpdateMaterialWithRequestFunction;
import com.keepitup.magjobbackend.material.service.impl.MaterialDefaultService;
import com.keepitup.magjobbackend.notification.entity.Notification;
import com.keepitup.magjobbackend.notification.service.impl.NotificationDefaultService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.impl.OrganizationDefaultService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final NotificationDefaultService notificationService;
    private final MaterialToResponseFunction materialToResponseFunction;
    private final MaterialsToResponseFunction materialsToResponseFunction;
    private final RequestToMaterialFunction requestToMaterialFunction;
    private final UpdateMaterialWithRequestFunction updateMaterialWithRequestFunction;

    @Autowired
    public MaterialDefaultController(
            MaterialDefaultService materialService,
            OrganizationDefaultService organizationService,
            NotificationDefaultService notificationService,
            MaterialToResponseFunction materialToResponseFunction,
            MaterialsToResponseFunction materialsToResponseFunction,
            RequestToMaterialFunction requestToMaterialFunction,
            UpdateMaterialWithRequestFunction updateMaterialWithRequestFunction
    ) {
        this.materialService = materialService;
        this.organizationService = organizationService;
        this.notificationService = notificationService;
        this.materialToResponseFunction = materialToResponseFunction;
        this.materialsToResponseFunction = materialsToResponseFunction;
        this.requestToMaterialFunction = requestToMaterialFunction;
        this.updateMaterialWithRequestFunction = updateMaterialWithRequestFunction;
    }

    @Override
    public GetMaterialsResponse getMaterials(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = materialService.findAll().size();
        return materialsToResponseFunction.apply(materialService.findAll(pageRequest), count);
    }

    @Override
    public GetMaterialResponse getMaterial(BigInteger id) {
        return materialService.find(id)
                .map(materialToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMaterialsResponse getMaterialsByOrganization(int page, int size, BigInteger organizationId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Integer count = materialService.findAllByOrganization(organization, Pageable.unpaged()).getNumberOfElements();

        return materialsToResponseFunction.apply(materialService.findAllByOrganization(organization, pageRequest), count);
    }

    @Override
    public GetMaterialResponse createMaterial(PostMaterialRequest postMaterialRequest) {
        materialService.create(requestToMaterialFunction.apply(postMaterialRequest));

        Optional<Organization> organizationOptional = organizationService.find(postMaterialRequest.getOrganization());

        organizationOptional.ifPresent(organization ->
                notificationService.create(Notification.builder()
                        .organization(organization)
                        .content("New material added in organization " + organization.getName())
                        .build()));

        return materialService.findByTitle(postMaterialRequest.getTitle())
                .map(materialToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetMaterialResponse updateMaterial(BigInteger id, PatchMaterialRequest patchMaterialRequest) {
        materialService.find(id)
                .ifPresentOrElse(
                        material -> {
                            materialService.update(updateMaterialWithRequestFunction.apply(material, patchMaterialRequest));

                            notificationService.create(Notification.builder()
                                    .organization(material.getOrganization())
                                    .content("Material updated in organization " + material.getOrganization().getName())
                                    .build());
                        },
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
                        material -> {
                            materialService.delete(id);

                            notificationService.create(Notification.builder()
                                    .organization(material.getOrganization())
                                    .content("Material deleted in organization " + material.getOrganization().getName())
                                    .build());
                        },
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }
}
