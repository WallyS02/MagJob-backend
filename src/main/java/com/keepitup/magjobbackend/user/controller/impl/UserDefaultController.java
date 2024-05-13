package com.keepitup.magjobbackend.user.controller.impl;

import com.keepitup.magjobbackend.jwt.CustomJwt;
import com.keepitup.magjobbackend.user.controller.api.UserController;
import com.keepitup.magjobbackend.user.dto.*;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.function.*;
import com.keepitup.magjobbackend.user.service.impl.UserDefaultService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Log
public class UserDefaultController implements UserController {
    private final UserDefaultService service;
    private final UserToResponseFunction userToResponse;
    private final UsersToResponseFunction usersToResponse;
    private final RequestToUserFunction requestToUser;
    private final UpdateUserWithRequestFunction updateUserWithRequest;
    private final UpdateUserPasswordWithRequestFunction updateUserPasswordWithRequestFunction;

    @Autowired
    public UserDefaultController(
            UserDefaultService service,
            UserToResponseFunction userToResponse,
            UsersToResponseFunction usersToResponse,
            RequestToUserFunction requestToUser,
            UpdateUserWithRequestFunction updateUserWithRequest,
            UpdateUserPasswordWithRequestFunction updateUserPasswordWithRequestFunction) {
        this.service = service;
        this.userToResponse = userToResponse;
        this.usersToResponse = usersToResponse;
        this.requestToUser = requestToUser;
        this.updateUserWithRequest = updateUserWithRequest;
        this.updateUserPasswordWithRequestFunction = updateUserPasswordWithRequestFunction;
    }

    @Override
    public GetUsersResponse getUsers() {
        return usersToResponse.apply(service.findAll());
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public GetUserResponse getUser(String externalId) {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserId = jwt.getExternalId();

        if (!loggedInUserId.equals(externalId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this profile.");
        }

        return service.findByExternalId(externalId)
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @PreAuthorize("permitAll()")
    public GetUserResponse createUser() {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = service.findByExternalId(jwt.getExternalId());

        PostUserRequest postUserRequest;
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            postUserRequest = new PostUserRequest();
            postUserRequest.setEmail(jwt.getEmail());
            postUserRequest.setFirstname(jwt.getFirstname());
            postUserRequest.setLastname(jwt.getLastname());
            postUserRequest.setExternalId(jwt.getExternalId());

            service.register(requestToUser.apply(postUserRequest));
        }

        return service.find(postUserRequest.getEmail())
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUser(String externalId) {
        service.findByExternalId(externalId)
                .ifPresentOrElse(
                        user -> service.deleteByExternalId(externalId),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetUserResponse updateUser(String externalId, PatchUserRequest patchUserRequest) {
        service.findByExternalId(externalId)
                .ifPresentOrElse(
                    user -> service.update(updateUserWithRequest.apply(user, patchUserRequest)),
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                    }
                );
        return getUser(externalId);
    }
}
