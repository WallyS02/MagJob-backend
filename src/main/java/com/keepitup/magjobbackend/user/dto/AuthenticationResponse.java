package com.keepitup.magjobbackend.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "AuthenticationResponse DTO")
public class AuthenticationResponse {

    @Schema(description = "JWT token value")
    private String jwt;

    @Schema(description = "GetUserResponse DTO")
    private GetUserResponse user;
}
