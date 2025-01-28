package com.authorization.auth_service.services.impl;

import com.authorization.auth_service.client.UserServiceClient;
import com.authorization.auth_service.services.RefreshTokenService;
import com.authorization.auth_service.models.auth.dto.request.TokenRefreshRequest;
import com.authorization.auth_service.models.auth.dto.response.TokenResponse;
import com.authorization.auth_service.models.common.response.CustomResponse;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserServiceClient userServiceClient;

    @Override
    public CustomResponse<TokenResponse> refreshToken(TokenRefreshRequest tokenRefreshRequest) {
        return userServiceClient.refreshToken(tokenRefreshRequest);
    }

}
