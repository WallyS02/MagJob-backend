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
@Schema(description = "PutPasswordRequest DTO")
public class PutPasswordRequest {

    @Schema(description = "User password value")
    private String password;

}