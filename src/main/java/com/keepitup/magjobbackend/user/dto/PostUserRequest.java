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
@Schema(description = "PostUserRequest DTO")
public class PostUserRequest {

    @Schema(description = "User email value")
    private String email;

    @Schema(description = "User external Id value")
    private String externalId;

    @Schema(description = "User firstname value")
    private String firstname;

    @Schema(description = "User lastname value")
    private String lastname;
}
