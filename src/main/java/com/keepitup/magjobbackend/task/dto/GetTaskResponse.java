package com.keepitup.magjobbackend.task.dto;

import jakarta.persistence.Column;
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
public class GetTaskResponse {

    private BigInteger id;

    private String title;
    private String description;

    private ZonedDateTime dateOfCreation;
    private ZonedDateTime dateOfCompletion;
    private ZonedDateTime deadLine;

    private Boolean isImportant;
    private Boolean isDone;

}
