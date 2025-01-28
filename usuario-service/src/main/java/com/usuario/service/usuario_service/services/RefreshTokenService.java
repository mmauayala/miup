package com.usuario.service.usuario_service.services;

import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.dto.request.TokenRefreshRequest;

public interface RefreshTokenService {

    Token refreshToken(final TokenRefreshRequest tokenRefreshRequest);

}