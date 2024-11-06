package com.keepitup.magjobbackend.notification.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetNotificationResponse DTO")
public class GetNotificationResponse {
    @Schema(description = "Notification id value")
    private BigInteger id;

    @Schema(description = "Notification content")
    private String content;

    @Schema(description = "Notification date of creation")
    private LocalDateTime dateOfCreation;

    @Schema(description = "Notification seen value")
    private boolean seen;

    @Schema(description = "Notification sent value")
    private boolean sent;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Organization {
        @Schema(description = "Organization id value")
        private BigInteger id;

        @Schema(description = "Organization name")
        private String name;
    }

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
    public static class User {
        @Schema(description = "User id value")
        private UUID id;

        @Schema(description = "User email value")
        private String email;
    }

    @Schema(description = "Organization class value")
    private Organization organization;

    @Schema(description = "Member class value")
    private Member member;

    @Schema(description = "User class value")
    private User user;
}
