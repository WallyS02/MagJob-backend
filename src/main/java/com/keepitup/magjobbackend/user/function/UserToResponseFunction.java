package com.keepitup.magjobbackend.user.function;

import com.keepitup.magjobbackend.user.dto.GetUserResponse;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserToResponseFunction implements Function<User, GetUserResponse> {

    @Override
    public GetUserResponse apply(User user) {
        return GetUserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .birthDate(user.getBirthDate())
                .build();
    }
}
