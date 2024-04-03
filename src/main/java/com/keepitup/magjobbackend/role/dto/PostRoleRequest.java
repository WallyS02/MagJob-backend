package com.keepitup.magjobbackend.role.dto;

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
@Schema(description = "PostRoleRequest DTO")
public class PostRoleRequest {

    @Schema(description = "Role name")
    private String name;

    @Schema(description = "Organization id value")
    private BigInteger organization;
}
