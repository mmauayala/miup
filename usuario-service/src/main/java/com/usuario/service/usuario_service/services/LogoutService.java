package com.usuario.service.usuario_service.services;

import com.usuario.service.usuario_service.model.user.dto.request.TokenInvalidateRequest;

public interface LogoutService {

    void logout(final TokenInvalidateRequest tokenInvalidateRequest);

}