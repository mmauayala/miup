package com.usuario.service.usuario_service.model.user;

import org.springframework.util.StringUtils;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Token {

    private final String accessToken;
    private final Long accessTokenExpiresAt;
    private final String refreshToken;

    private static final String TOKEN_PREFIX = "Bearer ";

    public static boolean isBearerToken(final String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) &&
                authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    public static String getJwt(final String authorizationHeader) {
        return authorizationHeader.replace(TOKEN_PREFIX, "");
    }

}