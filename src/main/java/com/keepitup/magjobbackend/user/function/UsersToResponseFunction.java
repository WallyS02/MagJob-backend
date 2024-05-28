package com.keepitup.magjobbackend.user.function;

import com.keepitup.magjobbackend.user.dto.GetUsersResponse;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;

@Component
public class UsersToResponseFunction implements BiFunction<Page<User>, Integer, GetUsersResponse> {

    @Override
    public GetUsersResponse apply(Page<User> users, Integer count) {
        return GetUsersResponse.builder()
                .users(users.stream()
                        .map(user -> GetUsersResponse.User.builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .build())
                        .toList())
                .count(count)
                .build();
    }
}
