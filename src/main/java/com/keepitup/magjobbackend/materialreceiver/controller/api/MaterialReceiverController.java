package com.keepitup.magjobbackend.materialreceiver.controller.api;

import com.keepitup.magjobbackend.configuration.PageConfig;
import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiverResponse;
import com.keepitup.magjobbackend.materialreceiver.dto.GetMaterialReceiversResponse;
import com.keepitup.magjobbackend.materialreceiver.dto.PatchMaterialReceiverRequest;
import com.keepitup.magjobbackend.materialreceiver.dto.PostMaterialReceiverRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

@Tag(name="Material Receiver Controller")
public interface MaterialReceiverController {
    PageConfig pageConfig = new PageConfig();

    @Operation(summary = "Get all Material Receivers")
    @GetMapping("api/material-receivers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialReceiversResponse getMaterialReceivers(
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

    @Operation(summary = "Get Material Receiver of given id")
    @GetMapping("api/material-receivers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialReceiverResponse getMaterialReceiver(
            @Parameter(
                    name = "id",
                    description = "Material Receiver id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );

    @Operation(summary = "Get Material Receivers By Material")
    @GetMapping("api/materials/{materialId}/material-receivers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialReceiversResponse getMaterialReceiversByMaterial(
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
                    name = "materialId",
                    description = "Material id value",
                    required = true
            )
            @PathVariable("materialId")
            BigInteger materialId
    );

    @Operation(summary = "Get Material Receivers By Member")
    @GetMapping("api/members/{memberId}/material-receivers")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialReceiversResponse getMaterialReceiversByMember(
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

    @Operation(summary = "Create Material Receiver")
    @PostMapping("api/material-receivers")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetMaterialReceiverResponse createMaterialReceiver(
            @Parameter(
                    name = "PostMaterialReceiverRequest",
                    description = "PostMaterialReceiverRequest DTO",
                    schema = @Schema(implementation = PostMaterialReceiverRequest.class),
                    required = true
            )
            @RequestBody
            PostMaterialReceiverRequest postMaterialReceiverRequest
    );

    @Operation(summary = "Update Material Receiver")
    @PatchMapping("api/material-receivers/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMaterialReceiverResponse updateMaterialReceiver(
            @Parameter(
                    name = "id",
                    description = "Material Receiver id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id,
            @Parameter(
                    name = "PatchMaterialReceiverRequest",
                    description = "PatchMaterialReceiverRequest DTO",
                    schema = @Schema(implementation = PatchMaterialReceiverRequest.class),
                    required = true
            )
            @RequestBody
            PatchMaterialReceiverRequest patchMaterialReceiverRequest
    );

    @Operation(summary = "Delete Material Receiver")
    @DeleteMapping("/api/material-receivers/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMaterialReceiver(
            @Parameter(
                    name = "id",
                    description = "Material Receiver id value",
                    required = true
            )
            @PathVariable("id")
            BigInteger id
    );
}
