package com.authorization.auth_service.services;

import com.authorization.auth_service.models.auth.User;
import com.authorization.auth_service.models.auth.dto.request.RegisterRequest;

public interface RegisterService {

    User registerUser(final RegisterRequest registerRequest);

}