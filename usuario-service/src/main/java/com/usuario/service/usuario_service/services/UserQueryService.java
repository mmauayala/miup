package com.usuario.service.usuario_service.services;

import java.util.List;
import java.util.Optional;

import com.usuario.service.usuario_service.model.user.dto.response.UserResponse;

public interface UserQueryService {

    Optional<UserResponse> getUserById(String id);

    Optional<UserResponse> getUserByUsername(String username);
    
    List<UserResponse> getAllUsers();

    UserResponse getCurrentUser(String token);
}
