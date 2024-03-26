package com.keepitup.magjobbackend.assignee.dto;

import lombok.*;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetAssigneesResponse {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Assignee {

        private BigInteger memberId;
        private BigInteger taskId;

    }

    @Singular
    private List<Assignee> assignees;
}
