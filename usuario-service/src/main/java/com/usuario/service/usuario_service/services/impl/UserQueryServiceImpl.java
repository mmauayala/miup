package com.usuario.service.usuario_service.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usuario.service.usuario_service.model.user.dto.response.UserResponse;
import com.usuario.service.usuario_service.model.user.entities.UserEntity;
import com.usuario.service.usuario_service.repositories.UserRepository;
import com.usuario.service.usuario_service.services.TokenService;
import com.usuario.service.usuario_service.services.UserQueryService;

import io.jsonwebtoken.Claims;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    // Obtener un usuario por su ID
    @Override
    public Optional<UserResponse> getUserById(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    // Obtener todos los usuarios
    @Override
    public List<UserResponse> getAllUsers() {
        return ((List<UserEntity>) userRepository.findAll())
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    // Obtener el usuario actual basado en el token
    @Override
    public UserResponse getCurrentUser(String token) {
        // Extraer el ID del usuario del token
        Claims claims = tokenService.getPayload(token);
        String userId = claims.get("userId", String.class); // Asegúrate de que "userId" sea el claim correcto

        // Buscar el usuario en la base de datos
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Mapear a UserResponse
        return mapToUserResponse(userEntity);
    }

    // Mapear UserEntity a UserResponse
    private UserResponse mapToUserResponse(UserEntity userEntity) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userEntity.getId());
        userResponse.setUsername(userEntity.getUsername());
        return userResponse;
    }

    
}
