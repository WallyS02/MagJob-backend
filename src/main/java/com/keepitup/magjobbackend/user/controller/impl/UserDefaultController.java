package com.keepitup.magjobbackend.user.controller.impl;

import com.keepitup.magjobbackend.configuration.SecurityService;
import com.keepitup.magjobbackend.jwt.CustomJwt;
import com.keepitup.magjobbackend.user.controller.api.UserController;
import com.keepitup.magjobbackend.user.dto.*;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.function.*;
import com.keepitup.magjobbackend.user.service.impl.UserDefaultService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@RestController
@Log
public class UserDefaultController implements UserController {
    private final UserDefaultService service;
    private final UserToResponseFunction userToResponse;
    private final UsersToResponseFunction usersToResponse;
    private final RequestToUserFunction requestToUser;
    private final UpdateUserWithRequestFunction updateUserWithRequest;
    private final SecurityService securityService;

    @Autowired
    public UserDefaultController(
            UserDefaultService service,
            UserToResponseFunction userToResponse,
            UsersToResponseFunction usersToResponse,
            RequestToUserFunction requestToUser,
            UpdateUserWithRequestFunction updateUserWithRequest,
            UpdateUserPasswordWithRequestFunction updateUserPasswordWithRequestFunction,
            SecurityService securityService
    ) {
        this.service = service;
        this.userToResponse = userToResponse;
        this.usersToResponse = usersToResponse;
        this.requestToUser = requestToUser;
        this.updateUserWithRequest = updateUserWithRequest;
        this.securityService = securityService;
    }

    @Override
    public GetUsersResponse getUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Integer count = service.findAll().size();
        return usersToResponse.apply(service.findAll(pageRequest), count);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public GetUserResponse getUser(UUID id) {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        UUID loggedInUserId = UUID.fromString(jwt.getExternalId());

        if (!loggedInUserId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this profile.");
        }

        return service.find(id)
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @PreAuthorize("permitAll()")
    public GetUserResponse createUser() {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();

        Optional<User> user = service.find(UUID.fromString(jwt.getExternalId()));

        PostUserRequest postUserRequest;
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            postUserRequest = new PostUserRequest();
            postUserRequest.setEmail(jwt.getEmail());
            postUserRequest.setFirstname(jwt.getFirstname());
            postUserRequest.setLastname(jwt.getLastname());
            postUserRequest.setId(UUID.fromString(jwt.getExternalId()));

            service.register(requestToUser.apply(postUserRequest));
        }

        return service.find(postUserRequest.getEmail())
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUser(UUID id) {
        if (!securityService.hasAdminPermission()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        service.find(id)
                .ifPresentOrElse(
                        user -> service.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetUserResponse updateUser(UUID id, PatchUserRequest patchUserRequest) {
        var jwt = (CustomJwt) SecurityContextHolder.getContext().getAuthentication();
        UUID loggedInUserId = UUID.fromString(jwt.getExternalId());

        if (!loggedInUserId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not authorized to access this profile.");
        }

        service.find(id)
                .ifPresentOrElse(
                    user -> service.update(updateUserWithRequest.apply(user, patchUserRequest)),
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                    }
                );
        return getUser(id);
    }
}
