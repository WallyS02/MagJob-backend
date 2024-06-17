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
@Schema(description = "PostAssigneeRequest DTO")
public class PostAssigneeRequest {

    @Schema(description = "Member id value")
    private BigInteger member;
    @Schema(description = "Task id value")
    private BigInteger task;

}
