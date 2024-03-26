package com.keepitup.magjobbackend.announcement.controller.api;

import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementResponse;
import com.keepitup.magjobbackend.announcement.dto.GetAnnouncementsResponse;
import com.keepitup.magjobbackend.announcement.dto.PatchAnnouncementRequest;
import com.keepitup.magjobbackend.announcement.dto.PostAnnouncementRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name="Announcement Controller")
public interface AnnouncementController {
    @Operation(summary = "Get all Announcements")
    @GetMapping("api/announcements")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementsResponse getAnnouncements();

    @Operation(summary = "Get Announcement of given id")
    @GetMapping("api/announcements/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementResponse getAnnouncement(
            @Parameter(
                    name = "id",
                    description = "Announcement id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Get Announcements By Organization")
    @GetMapping("api/organizations/{organizationId}/announcements")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementsResponse getAnnouncementsByOrganization(
            @Parameter(
                    name = "organizationId",
                    description = "Organization id value",
                    required = true
            )
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @Operation(summary = "Create Announcement")
    @PostMapping("api/announcements")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetAnnouncementResponse createAnnouncement(
            @Parameter(
                    name = "PostAnnouncementRequest",
                    description = "PostAnnouncementRequest DTO",
                    schema = @Schema(implementation = PostAnnouncementRequest.class),
                    required = true
            )
            @RequestBody
            PostAnnouncementRequest postAnnouncementRequest
    );

    @Operation(summary = "Update Announcement")
    @PatchMapping("api/announcements/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementResponse updateAnnouncement(
            @Parameter(
                    name = "id",
                    description = "Announcement id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id,
            @Parameter(
                    name = "PatchAnnouncementRequest",
                    description = "PatchAnnouncementRequest DTO",
                    schema = @Schema(implementation = PatchAnnouncementRequest.class),
                    required = true
            )
            @RequestBody
            PatchAnnouncementRequest patchAnnouncementRequest
    );

    @Operation(summary = "Delete Announcement")
    @DeleteMapping("/api/announcements/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAnnouncement(
            @Parameter(
                    name = "id",
                    description = "Announcement id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );
}
