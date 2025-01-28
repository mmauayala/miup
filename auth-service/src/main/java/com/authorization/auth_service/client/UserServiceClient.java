package com.authorization.auth_service.client;

import com.authorization.auth_service.models.auth.User;
import com.authorization.auth_service.models.auth.dto.request.LoginRequest;
import com.authorization.auth_service.models.auth.dto.request.RegisterRequest;
import com.authorization.auth_service.models.auth.dto.request.TokenInvalidateRequest;
import com.authorization.auth_service.models.auth.dto.request.TokenRefreshRequest;
import com.authorization.auth_service.models.auth.dto.response.TokenResponse;
import com.authorization.auth_service.models.common.response.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario-service", path = "/v1/usuarios")
public interface UserServiceClient {

    @PostMapping("/register")
    ResponseEntity<User> register(@RequestBody @Valid final RegisterRequest request);

    @PostMapping("/validate-token")
    void validateToken(@RequestParam String token);

    @PostMapping("/login")
    CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest);

    @PostMapping("/refresh-token")
    CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest);

    @PostMapping("/logout")
    CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest);

}