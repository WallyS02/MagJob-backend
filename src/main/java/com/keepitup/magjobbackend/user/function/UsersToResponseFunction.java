package com.keepitup.magjobbackend.user.function;

import com.keepitup.magjobbackend.user.dto.GetUsersResponse;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UsersToResponseFunction implements Function<Page<User>, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(Page<User> users) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .build())
                        .toList()).
                build();
    }
}
