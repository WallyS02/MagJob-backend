package com.keepitup.magjobbackend.announcementreceiver.controller.api;

import com.keepitup.magjobbackend.announcementreceiver.dto.GetAnnouncementReceiverResponse;
import com.keepitup.magjobbackend.announcementreceiver.dto.GetAnnouncementReceiversResponse;
import com.keepitup.magjobbackend.announcementreceiver.dto.PatchAnnouncementReceiverRequest;
import com.keepitup.magjobbackend.announcementreceiver.dto.PostAnnouncementReceiverRequest;
import com.keepitup.magjobbackend.configuration.PageConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name="Announcement Receiver Controller")
public interface AnnouncementReceiverController {
    PageConfig pageConfig = new PageConfig();

    @Operation(summary = "Get all Announcement Receivers")
    @GetMapping("api/announcement-receivers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementReceiversResponse getAnnouncementReceivers(
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

    @Operation(summary = "Get Announcement Receiver of given id")
    @GetMapping("api/announcement-receivers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementReceiverResponse getAnnouncementReceiver(
            @Parameter(
                    name = "id",
                    description = "Announcement Receiver id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Get Announcement Receivers By Announcement")
    @GetMapping("api/announcements/{announcementId}/announcement-receivers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementReceiversResponse getAnnouncementReceiversByAnnouncement(
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
                    name = "announcementId",
                    description = "Announcement id value",
                    required = true
            )
            @PathVariable("announcementId")
            BigInteger announcementId
    );

    @Operation(summary = "Get Announcement Receivers By Member")
    @GetMapping("api/members/{memberId}/announcement-receivers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementReceiversResponse getAnnouncementReceiversByMember(
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

    @Operation(summary = "Create Announcement Receiver")
    @PostMapping("api/announcement-receivers")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetAnnouncementReceiverResponse createAnnouncementReceiver(
            @Parameter(
                    name = "PostAnnouncementReceiverRequest",
                    description = "PostAnnouncementReceiverRequest DTO",
                    schema = @Schema(implementation = PostAnnouncementReceiverRequest.class),
                    required = true
            )
            @RequestBody
            PostAnnouncementReceiverRequest postAnnouncementReceiverRequest
    );

    @Operation(summary = "Update Announcement Receiver")
    @PatchMapping("api/announcement-receivers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetAnnouncementReceiverResponse updateAnnouncementReceiver(
            @Parameter(
                    name = "id",
                    description = "Announcement Receiver id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id,
            @Parameter(
                    name = "PatchAnnouncementReceiverRequest",
                    description = "PatchAnnouncementReceiverRequest DTO",
                    schema = @Schema(implementation = PatchAnnouncementReceiverRequest.class),
                    required = true
            )
            @RequestBody
            PatchAnnouncementReceiverRequest patchAnnouncementReceiverRequest
    );

    @Operation(summary = "Delete Announcement Receiver")
    @DeleteMapping("/api/announcement-receivers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteAnnouncementReceiver(
            @Parameter(
                    name = "id",
                    description = "Announcement Receiver id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );
}
