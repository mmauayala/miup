package com.usuario.service.usuario_service.services;

import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.dto.request.LoginRequest;

public interface UserLoginService {

    Token login(final LoginRequest loginRequest);

}