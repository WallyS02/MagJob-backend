package com.keepitup.magjobbackend.announcement.controller.impl;

import com.keepitup.magjobbackend.announcement.controller.api.AnnouncementController;
import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementResponse;
import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementsResponse;
import com.keepitup.magjobbackend.announcement.dto.PatchAnnouncementRequest;
import com.keepitup.magjobbackend.announcement.dto.PostAnnouncementRequest;
import com.keepitup.magjobbackend.announcement.entity.Announcement;
import com.keepitup.magjobbackend.announcement.function.AnnouncementToResponseFunction;
import com.keepitup.magjobbackend.announcement.function.AnnouncementsToResponseFunction;
import com.keepitup.magjobbackend.announcement.function.RequestToAnnouncementFunction;
import com.keepitup.magjobbackend.announcement.function.UpdateAnnouncementWithRequestFunction;
import com.keepitup.magjobbackend.announcement.service.impl.AnnouncementDefaultService;
import com.keepitup.magjobbackend.configuration.Constants;
import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.organization.entity.Organization;
import com.keepitup.magjobbackend.organization.service.impl.OrganizationDefaultService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.util.Optional;

@RestController
@Log
public class AnnouncementDefaultController implements AnnouncementController {
    private final AnnouncementDefaultService announcementService;
    private final OrganizationDefaultService organizationService;
    private final AnnouncementsToResponseFunction announcementsToResponseFunction;
    private final AnnouncementToResponseFunction announcementToResponseFunction;
    private final RequestToAnnouncementFunction requestToAnnouncementFunction;
    private final UpdateAnnouncementWithRequestFunction updateAnnouncementWithRequestFunction;
    private final SecurityService securityService;

    @Autowired
    public AnnouncementDefaultController(
            AnnouncementDefaultService announcementService,
            OrganizationDefaultService organizationService,
            AnnouncementsToResponseFunction announcementsToResponseFunction,
            AnnouncementToResponseFunction announcementToResponseFunction,
            RequestToAnnouncementFunction requestToAnnouncementFunction,
            UpdateAnnouncementWithRequestFunction updateAnnouncementWithRequestFunction,
            SecurityService securityService
    ) {
        this.announcementService = announcementService;
        this.organizationService = organizationService;
        this.announcementsToResponseFunction = announcementsToResponseFunction;
        this.announcementToResponseFunction = announcementToResponseFunction;
        this.requestToAnnouncementFunction = requestToAnnouncementFunction;
        this.updateAnnouncementWithRequestFunction = updateAnnouncementWithRequestFunction;
        this.securityService = securityService;
    }

    @Override
    public GetAnnouncementsResponse getAnnouncements(int page, int size) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
  
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = announcementService.findAll().size();
        return announcementsToResponseFunction.apply(announcementService.findAll(pageRequest), count);
    }

    @Override
    public GetAnnouncementResponse getAnnouncement(BigInteger id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return announcementService.find(id)
                .map(announcementToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetAnnouncementsResponse getAnnouncementsByOrganization(int page, int size, BigInteger organizationId) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Optional<Organization> organizationOptional = organizationService.find(organizationId);

        Organization organization = organizationOptional
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if(!securityService.belongsToOrganization(organization)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
      
        Integer count = announcementService.findAllByOrganization(organization, pageRequest).getNumberOfElements();

        return announcementsToResponseFunction.apply(announcementService.findAllByOrganization(organization, pageRequest), count);
    }

    @Override
    public GetAnnouncementResponse createAnnouncement(PostAnnouncementRequest postAnnouncementRequest) {
        Optional<Organization> organization = organizationService.find(postAnnouncementRequest.getOrganization());

        if (organization.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!securityService.hasPermission(organization.get(), Constants.PERMISSION_NAME_CAN_MANAGE_ANNOUNCEMENTS)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        announcementService.create(requestToAnnouncementFunction.apply(postAnnouncementRequest));
        return announcementService.findByTitle(postAnnouncementRequest.getTitle())
                .map(announcementToResponseFunction)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetAnnouncementResponse updateAnnouncement(BigInteger id, PatchAnnouncementRequest patchAnnouncementRequest) {
        Announcement announcement = announcementService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Optional<Organization> organization = organizationService.find(announcement.getOrganization().getId());

        if (organization.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!securityService.hasPermission(organization.get(), Constants.PERMISSION_NAME_CAN_MANAGE_ANNOUNCEMENTS)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        announcementService.update(updateAnnouncementWithRequestFunction.apply(announcement, patchAnnouncementRequest));
        return getAnnouncement(id);
    }

    @Override
    public void deleteAnnouncement(BigInteger id) {
        Announcement announcement = announcementService.find(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        Optional<Organization> organization = organizationService.find(announcement.getOrganization().getId());

        if (organization.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (!securityService.hasPermission(organization.get(), Constants.PERMISSION_NAME_CAN_MANAGE_ANNOUNCEMENTS)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        announcementService.delete(id);
    }
}
