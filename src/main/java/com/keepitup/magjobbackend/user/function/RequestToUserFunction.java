package com.keepitup.magjobbackend.user.function;

import com.keepitup.magjobbackend.user.dto.PostUserRequest;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class RequestToUserFunction implements Function<PostUserRequest, User> {

    @Override
    public User apply(PostUserRequest request) {
        return User.builder()
                .email(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(request.getPassword())
                .build();
    }
}
