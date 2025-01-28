package com.usuario.service.usuario_service.services;

import com.usuario.service.usuario_service.model.user.User;
import com.usuario.service.usuario_service.model.user.dto.request.RegisterRequest;

public interface RegisterService {

    /**
     * Registers a new user with the provided registration request.
     *
     * @param registerRequest the request containing user registration details.
     * @return the registered {@link User} instance.
     */
    User registerUser(final RegisterRequest registerRequest);

}
