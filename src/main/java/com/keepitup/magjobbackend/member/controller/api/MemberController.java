package com.keepitup.magjobbackend.member.controller.api;

import com.keepitup.magjobbackend.member.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

public interface MemberController {


    @GetMapping("api/members")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMembersResponse getMembers();

    @GetMapping("api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMemberResponse getMember(
            @PathVariable("id")
            BigInteger id
    );

    @GetMapping("api/organizations/{organizationId}/members")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMembersResponse getMembersByOrganization(
            @PathVariable("organizationId")
            BigInteger organizationId
    );

    @PostMapping("api/members")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    GetMemberResponse createMember(
            @RequestBody
            PostMemberRequest postMemberRequest
    );

    @PatchMapping("api/members/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    GetMemberResponse updateMember(
            @PathVariable("id")
            BigInteger id,
            @RequestBody
            PatchMemberRequest patchMemberRequest
    );

    @DeleteMapping("/api/members/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMember(
            @PathVariable("id")
            BigInteger id
    );
}
