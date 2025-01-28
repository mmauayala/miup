package com.authorization.auth_service.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.auth_service.models.auth.dto.request.LoginRequest;
import com.authorization.auth_service.models.auth.dto.request.RegisterRequest;
import com.authorization.auth_service.models.auth.dto.request.TokenInvalidateRequest;
import com.authorization.auth_service.models.auth.dto.request.TokenRefreshRequest;
import com.authorization.auth_service.models.auth.dto.response.TokenResponse;
import com.authorization.auth_service.models.common.response.CustomResponse;
import com.authorization.auth_service.services.RefreshTokenService;
import com.authorization.auth_service.services.impl.LogoutServiceImpl;
import com.authorization.auth_service.services.impl.RegisterServiceImpl;
import com.authorization.auth_service.services.impl.UserLoginServiceImpl;

@RestController
@RequestMapping("/v1/autenticacion/usuarios")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterServiceImpl registerService;

    private final UserLoginServiceImpl userLoginService;

    private final RefreshTokenService refreshTokenService;

    private final LogoutServiceImpl logoutService;

    @PostMapping("/register")
    public CustomResponse<Void> registerAdmin(@RequestBody @Valid final RegisterRequest registerRequest) {
        registerService.registerUser(registerRequest);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest) {
        return userLoginService.login(loginRequest);
    }

    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        return refreshTokenService.refreshToken(tokenRefreshRequest);
    }

    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

}
