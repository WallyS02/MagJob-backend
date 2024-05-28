package com.keepitup.magjobbackend.assignee.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetAssigneeResponse DTO")
public class GetAssigneeResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Member {

        @Schema(description = "Member id value")
        private BigInteger id;

        @Schema(description = "Member pseudonym")
        private String pseudonym;

    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Task {

        @Schema(description = "Task id value")
        private BigInteger id;

        @Schema(description = "Task title")
        private String title;

    }

    @Schema(description = "Task object")
    private Task task;
    @Schema(description = "Member object")
    private Member member;
}
