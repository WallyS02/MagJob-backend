package com.keepitup.magjobbackend.assignee.dto;

import lombok.*;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PostAssigneeRequest {

    private BigInteger member;
    private BigInteger task;

}
