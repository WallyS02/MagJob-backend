package com.keepitup.magjobbackend.task.dto;

import lombok.*;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetTasksResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Task {

        private BigInteger id;

        private String title;
        private String description;

        private ZonedDateTime dateOfCreation;
        private ZonedDateTime dateOfCompletion;
        private ZonedDateTime deadLine;

        private Boolean isImportant;
        private Boolean isDone;
    }

    @Singular
    private List<Task> tasks;
}
