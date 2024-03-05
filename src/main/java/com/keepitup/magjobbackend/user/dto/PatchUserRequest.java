package com.keepitup.magjobbackend.user.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchUserRequest {

    private String email;
    private String firstname;
    private String lastname;
    private String phoneNumber;

    private LocalDate birthDate;

}
