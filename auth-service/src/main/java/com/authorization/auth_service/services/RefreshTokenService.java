package com.authorization.auth_service.services;

import jakarta.validation.Valid;

import com.authorization.auth_service.models.auth.dto.request.TokenRefreshRequest;
import com.authorization.auth_service.models.auth.dto.response.TokenResponse;
import com.authorization.auth_service.models.common.response.CustomResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface RefreshTokenService {
    
    CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest);

}