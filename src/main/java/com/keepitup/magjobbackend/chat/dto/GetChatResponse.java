package com.keepitup.magjobbackend.chat.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetChatResponse DTO")
public class GetChatResponse {
    @Schema(description = "Chat id value")
    private BigInteger id;

    @Schema(description = "Chat title")
    private String title;

    @Schema(description = "Chat date of creation")
    private LocalDate dateOfCreation;

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

    @Schema(description = "Organization class value")
    private Organization organization;
}
