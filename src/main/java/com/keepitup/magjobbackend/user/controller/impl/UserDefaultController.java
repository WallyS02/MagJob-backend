package com.keepitup.magjobbackend.user.controller.impl;

import com.keepitup.magjobbackend.user.controller.api.UserController;
import com.keepitup.magjobbackend.user.dto.*;
import com.keepitup.magjobbackend.user.entity.User;
import com.keepitup.magjobbackend.user.function.*;
import com.keepitup.magjobbackend.user.service.impl.AuthenticationUserService;
import com.keepitup.magjobbackend.user.service.impl.UserDefaultService;
import com.keepitup.magjobbackend.user.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@Log
public class UserDefaultController implements UserController {
    private final UserDefaultService service;
    private final AuthenticationUserService authenticationService;
    private final UserToResponseFunction userToResponse;
    private final UsersToResponseFunction usersToResponse;
    private final RequestToUserFunction requestToUser;
    private final UpdateUserWithRequestFunction updateUserWithRequest;
    private final UpdateUserPasswordWithRequestFunction updateUserPasswordWithRequestFunction;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public UserDefaultController(
            UserDefaultService service,
            AuthenticationUserService authenticationService, UserToResponseFunction userToResponse,
            UsersToResponseFunction usersToResponse,
            RequestToUserFunction requestToUser,
            UpdateUserWithRequestFunction updateUserWithRequest,
            UpdateUserPasswordWithRequestFunction updateUserPasswordWithRequestFunction,
            AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.service = service;
        this.authenticationService = authenticationService;
        this.userToResponse = userToResponse;
        this.usersToResponse = usersToResponse;
        this.requestToUser = requestToUser;
        this.updateUserWithRequest = updateUserWithRequest;
        this.updateUserPasswordWithRequestFunction = updateUserPasswordWithRequestFunction;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public GetUsersResponse getUsers() {
        return usersToResponse.apply(service.findAll());
    }

    @Override
    public GetUserResponse getUser(BigInteger id) {
        return service.find(id)
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public GetUserResponse createUser(PostUserRequest postUserRequest) {
        Optional<User> user = service.find(postUserRequest.getEmail());
        if (user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        else {
            service.register(requestToUser.apply(postUserRequest));
        }
        return service.find(postUserRequest.getEmail())
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteUser(BigInteger id) {
        service.find(id)
                .ifPresentOrElse(
                        user -> service.delete(id),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public GetUserResponse updateUser(BigInteger id, PatchUserRequest patchUserRequest) {
        service.find(id)
                .ifPresentOrElse(
                    user -> service.update(updateUserWithRequest.apply(user, patchUserRequest)),
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                    }
                );
        return getUser(id);
    }

    public void updateUserPassword(BigInteger id, PutPasswordRequest putPasswordRequest) {
        service.find(id)
                .ifPresentOrElse(
                        user -> service.update(updateUserPasswordWithRequestFunction.apply(user, putPasswordRequest)),
                        () -> {
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                        }
                );
    }

    @Override
    public AuthenticationResponse createAuthenticationToken(AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException, IOException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect email or password");
        } catch (DisabledException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found, register user first");
            return null;
        }
        final UserDetails userDetails = authenticationService.loadUserByUsername(authenticationRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
        final GetUserResponse user = service.find(authenticationRequest.getEmail())
                .map(userToResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return AuthenticationResponse.builder()
                .jwt(jwt)
                .user(user)
                .build();
    }
}
