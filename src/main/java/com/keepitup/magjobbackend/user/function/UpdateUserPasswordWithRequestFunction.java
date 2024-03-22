package com.keepitup.magjobbackend.user.function;

import com.keepitup.magjobbackend.user.dto.PutPasswordRequest;
import com.keepitup.magjobbackend.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
@Component
public class UpdateUserPasswordWithRequestFunction implements BiFunction<User, PutPasswordRequest, User> {
    @Override
    public User apply(User entity, PutPasswordRequest request) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .phoneNumber(entity.getPhoneNumber())
                .birthDate(entity.getBirthDate())
                .build();
    }
}
