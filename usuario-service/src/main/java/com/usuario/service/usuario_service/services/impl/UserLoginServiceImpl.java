package com.usuario.service.usuario_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usuario.service.usuario_service.exception.PasswordNotValidException;
import com.usuario.service.usuario_service.exception.UserNotFoundException;
import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.dto.request.LoginRequest;
import com.usuario.service.usuario_service.model.user.entities.UserEntity;
import com.usuario.service.usuario_service.repositories.UserRepository;
import com.usuario.service.usuario_service.services.TokenService;
import com.usuario.service.usuario_service.services.UserLoginService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public Token login(LoginRequest loginRequest) {

        final UserEntity userEntityFromDB = userRepository
                .findByUsername(loginRequest.getUsername())
                .orElseThrow(
                        () -> new UserNotFoundException("Can't find with given username: "
                                + loginRequest.getUsername())
                );

        if (Boolean.FALSE.equals(passwordEncoder.matches(
                loginRequest.getPassword(), userEntityFromDB.getPassword()))) {
            throw new PasswordNotValidException();
        }

        return tokenService.generateToken(userEntityFromDB.getClaims());

    }

}