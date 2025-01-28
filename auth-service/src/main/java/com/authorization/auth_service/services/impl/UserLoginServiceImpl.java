package com.authorization.auth_service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.authorization.auth_service.models.auth.dto.request.LoginRequest;
import com.authorization.auth_service.models.auth.dto.response.TokenResponse;
import com.authorization.auth_service.models.common.response.CustomResponse;

import com.authorization.auth_service.client.UserServiceClient;
import com.authorization.auth_service.services.UserLoginService;


@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    private final UserServiceClient userServiceClient;

    @Override
    public CustomResponse<TokenResponse> login(LoginRequest loginRequest) {
        return userServiceClient.loginUser(loginRequest);
    }

}