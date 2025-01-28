package com.usuario.service.usuario_service.services.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.service.usuario_service.exception.TokenAlreadyInvalidatedException;
import com.usuario.service.usuario_service.model.user.entities.InvalidTokenEntity;
import com.usuario.service.usuario_service.repositories.InvalidTokenRepository;
import com.usuario.service.usuario_service.services.InvalidTokenService;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvalidTokenServiceImpl implements InvalidTokenService {

    @Autowired
    private InvalidTokenRepository invalidTokenRepository;

    @Override
    public void invalidateTokens(Set<String> tokenIds) {

        final Set<InvalidTokenEntity> enocaInvalidTokenEntities = tokenIds.stream()
                .map(tokenId -> InvalidTokenEntity.builder()
                        .tokenId(tokenId)
                        .build()
                )
                .collect(Collectors.toSet());

        invalidTokenRepository.saveAll(enocaInvalidTokenEntities);
    }

    @Override
    public void checkForInvalidityOfToken(String tokenId) {

        final boolean isTokenInvalid = invalidTokenRepository.findByTokenId(tokenId).isPresent();

        if (isTokenInvalid) {
            throw new TokenAlreadyInvalidatedException(tokenId);
        }

    }

}