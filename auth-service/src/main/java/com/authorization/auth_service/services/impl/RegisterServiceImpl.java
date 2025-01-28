package com.authorization.auth_service.services.impl;

import org.springframework.stereotype.Service;

import com.authorization.auth_service.client.UserServiceClient;
import com.authorization.auth_service.models.auth.User;
import com.authorization.auth_service.models.auth.dto.request.RegisterRequest;
import com.authorization.auth_service.services.RegisterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final UserServiceClient userServiceClient;

    @Override
    public User registerUser(RegisterRequest registerRequest) {
        return userServiceClient.register(registerRequest).getBody();
    }

}