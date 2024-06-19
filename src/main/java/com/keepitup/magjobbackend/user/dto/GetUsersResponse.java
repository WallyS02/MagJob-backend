package com.keepitup.magjobbackend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "GetUsersResponse DTO")
public class GetUsersResponse {

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

        @Schema(description = "User first name value")
        private String firstName;

        @Schema(description = "User last name value")
        private String lastName;
    }

    @Singular
    @Schema(description = "User list")
    private List<User> users;

    @Schema(description = "Number of all objects")
    private Integer count;
}