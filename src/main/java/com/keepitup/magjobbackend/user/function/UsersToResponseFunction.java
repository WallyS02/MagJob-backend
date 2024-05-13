package com.keepitup.magjobbackend.user.function;

import com.keepitup.magjobbackend.user.dto.GetUsersResponse;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class UsersToResponseFunction implements Function<List<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(List<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .externalId(user.getExternalId())
                                .email(user.getEmail())
                                .build())
                        .toList()).
                build();
    }
}
