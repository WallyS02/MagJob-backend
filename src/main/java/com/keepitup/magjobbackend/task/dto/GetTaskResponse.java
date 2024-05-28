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
@Schema(description = "GetTaskResponse DTO")
public class GetTaskResponse {

    @Schema(description = "Task id value")
    private BigInteger id;

    @Schema(description = "Task title")
    private String title;
    @Schema(description = "Task description")
    private String description;

    @Schema(description = "Task date of creation")
    private ZonedDateTime dateOfCreation;
    @Schema(description = "Task date of completion")
    private ZonedDateTime dateOfCompletion;
    @Schema(description = "Task deadline")
    private ZonedDateTime deadLine;

    @Schema(description = "Task is important?")
    private Boolean isImportant;
    @Schema(description = "Task is done?")
    private Boolean isDone;

}
