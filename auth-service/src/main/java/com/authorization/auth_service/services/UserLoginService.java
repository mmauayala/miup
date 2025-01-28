package com.authorization.auth_service.services;

import com.authorization.auth_service.models.auth.dto.request.LoginRequest;
import com.authorization.auth_service.models.auth.dto.response.TokenResponse;
import com.authorization.auth_service.models.common.response.CustomResponse;

public interface UserLoginService {
 
    CustomResponse<TokenResponse> login(final LoginRequest loginRequest);
}

