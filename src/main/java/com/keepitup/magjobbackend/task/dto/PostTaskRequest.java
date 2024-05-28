package com.keepitup.magjobbackend.task.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostTaskRequest {

    @Schema(description = "Task title")
    private String title;
    @Schema(description = "Task description")
    private String description;

    @Schema(description = "Task deadline")
    private ZonedDateTime deadLine;

    @Schema(description = "Task is important?")
    private Boolean isImportant;

    @Schema(description = "Organization id value")
    private BigInteger organization;

}
