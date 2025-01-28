package com.usuario.service.usuario_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.service.usuario_service.exception.UserNotFoundException;
import com.usuario.service.usuario_service.exception.UserStatusNotValidException;
import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.dto.request.TokenRefreshRequest;
import com.usuario.service.usuario_service.model.user.entities.UserEntity;
import com.usuario.service.usuario_service.model.user.enums.TokenClaims;
import com.usuario.service.usuario_service.model.user.enums.UserStatus;
import com.usuario.service.usuario_service.repositories.UserRepository;
import com.usuario.service.usuario_service.services.RefreshTokenService;
import com.usuario.service.usuario_service.services.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Override
    public Token refreshToken(TokenRefreshRequest tokenRefreshRequest) {

        tokenService.verifyAndValidate(tokenRefreshRequest.getRefreshToken());

        final String adminId = tokenService
                .getPayload(tokenRefreshRequest.getRefreshToken())
                .get(TokenClaims.USER_ID.getValue())
                .toString();

        final UserEntity userEntityFromDB = userRepository
                .findById(adminId)
                .orElseThrow(UserNotFoundException::new);

        this.validateUserStatus(userEntityFromDB);

        return tokenService.generateToken(
                userEntityFromDB.getClaims(),
                tokenRefreshRequest.getRefreshToken()
        );

    }

    private void validateUserStatus(final UserEntity userEntity) {
        if (!(UserStatus.ACTIVE.equals(userEntity.getUserStatus()))) {
            throw new UserStatusNotValidException("UserStatus = " + userEntity.getUserStatus());
        }
    }

}