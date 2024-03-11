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
@Schema(description = "AuthenticationRequest DTO")
public class AuthenticationRequest {

    @Schema(description = "User email value")
    private String email;

    @Schema(description = "User password value")
    private String password;

}
