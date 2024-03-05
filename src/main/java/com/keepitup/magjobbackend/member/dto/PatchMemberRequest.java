package com.keepitup.magjobbackend.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Schema(description = "PatchMemberRequest DTO")
public class PatchMemberRequest {

    @Schema(description = "Member pseudonym value")
    private String pseudonym;

}