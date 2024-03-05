package com.keepitup.magjobbackend.organization.dto;

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
public class GetOrganizationResponse {

    private BigInteger id;

    private ZonedDateTime dateOfCreation;

    private String name;
    private String profileBannerUrl;
}
