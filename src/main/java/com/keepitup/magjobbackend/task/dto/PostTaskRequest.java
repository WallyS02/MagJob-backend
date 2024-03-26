package com.keepitup.magjobbackend.task.dto;

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

    private String title;
    private String description;

    private ZonedDateTime deadLine;

    private Boolean isImportant;

    private BigInteger organization;

}
