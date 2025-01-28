package com.authorization.auth_service.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.authorization.auth_service.client.UserServiceClient;
import com.authorization.auth_service.models.auth.dto.request.TokenInvalidateRequest;
import com.authorization.auth_service.models.common.response.CustomResponse;
import com.authorization.auth_service.services.LogoutService;


@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    private final UserServiceClient userServiceClient;

    @Override
    public CustomResponse<Void> logout (TokenInvalidateRequest tokenInvalidateRequest) {
        return userServiceClient.logout(tokenInvalidateRequest);
    }

}

