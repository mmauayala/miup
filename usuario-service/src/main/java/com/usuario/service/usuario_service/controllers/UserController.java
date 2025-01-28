package com.usuario.service.usuario_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usuario.service.usuario_service.model.common.response.CustomResponse;
import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.dto.request.LoginRequest;
import com.usuario.service.usuario_service.model.user.dto.request.RegisterRequest;
import com.usuario.service.usuario_service.model.user.dto.request.TokenInvalidateRequest;
import com.usuario.service.usuario_service.model.user.dto.request.TokenRefreshRequest;
import com.usuario.service.usuario_service.model.user.dto.response.TokenResponse;
import com.usuario.service.usuario_service.model.user.mapper.TokenToTokenResponseMapper;
import com.usuario.service.usuario_service.services.impl.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/v1/usuarios")
public class UserController {

    @Autowired
    private UserLoginServiceImpl userLoginService;

    @Autowired
    private RegisterServiceImpl registerService;
    
    @Autowired
    private LogoutServiceImpl logoutService;

    @Autowired
    private RefreshTokenServiceImpl refreshTokenService;

    @Autowired
    private TokenServiceImpl tokenService;

    private final TokenToTokenResponseMapper tokenToTokenResponseMapper = TokenToTokenResponseMapper.initialize();

    @PostMapping("/register")
    public CustomResponse<Void> registerUser(@RequestBody @Validated final RegisterRequest registerRequest) {
        log.info("UserController | registerUser");
        registerService.registerUser(registerRequest);
        return CustomResponse.SUCCESS;
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestParam String token) {
        log.info("UserController | validateToken");
        tokenService.verifyAndValidate(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public CustomResponse<TokenResponse> loginUser(@RequestBody @Valid final LoginRequest loginRequest) {
        log.info("UserController | validateToken");
        final Token token = userLoginService.login(loginRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/refresh-token")
    public CustomResponse<TokenResponse> refreshToken(@RequestBody @Valid final TokenRefreshRequest tokenRefreshRequest) {
        log.info("UserController | refreshToken");
        final Token token = refreshTokenService.refreshToken(tokenRefreshRequest);
        final TokenResponse tokenResponse = tokenToTokenResponseMapper.map(token);
        return CustomResponse.successOf(tokenResponse);
    }

    @PostMapping("/logout")
    public CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest) {
        log.info("UserController | logout");
        logoutService.logout(tokenInvalidateRequest);
        return CustomResponse.SUCCESS;
    }

    @GetMapping("/authenticate")
    public ResponseEntity<UsernamePasswordAuthenticationToken> getAuthentication(@RequestParam String token) {
        UsernamePasswordAuthenticationToken authentication = tokenService.getAuthentication(token);
        return ResponseEntity.ok(authentication);
    }

}
