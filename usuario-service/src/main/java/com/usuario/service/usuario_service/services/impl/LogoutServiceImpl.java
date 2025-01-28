package com.usuario.service.usuario_service.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.service.usuario_service.model.user.dto.request.TokenInvalidateRequest;
import com.usuario.service.usuario_service.services.InvalidTokenService;
import com.usuario.service.usuario_service.services.LogoutService;
import com.usuario.service.usuario_service.services.TokenService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

    @Autowired
    private TokenService tokenService;
    
    @Autowired
    private InvalidTokenService invalidTokenService;

    @Override
    public void logout(TokenInvalidateRequest tokenInvalidateRequest) {

        tokenService.verifyAndValidate(
                Set.of(
                        tokenInvalidateRequest.getAccessToken(),
                        tokenInvalidateRequest.getRefreshToken()
                )
        );

        final String accessTokenId = tokenService
                .getPayload(tokenInvalidateRequest.getAccessToken())
                .getId();

        invalidTokenService.checkForInvalidityOfToken(accessTokenId);


        final String refreshTokenId = tokenService
                .getPayload(tokenInvalidateRequest.getRefreshToken())
                .getId();

        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        invalidTokenService.invalidateTokens(Set.of(accessTokenId,refreshTokenId));

    }

}