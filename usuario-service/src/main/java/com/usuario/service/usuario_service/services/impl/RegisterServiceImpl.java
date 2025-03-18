package com.usuario.service.usuario_service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.usuario.service.usuario_service.exception.UserAlreadyExistException;
import com.usuario.service.usuario_service.model.user.User;
import com.usuario.service.usuario_service.model.user.dto.request.RegisterRequest;
import com.usuario.service.usuario_service.model.user.entities.UserEntity;
import com.usuario.service.usuario_service.model.user.mapper.RegisterRequestToUserEntityMapper;
import com.usuario.service.usuario_service.model.user.mapper.UserEntityToUserMapper;
import com.usuario.service.usuario_service.repositories.UserRepository;
import com.usuario.service.usuario_service.services.RegisterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private final RegisterRequestToUserEntityMapper registerRequestToUserEntityMapper = RegisterRequestToUserEntityMapper.initialize();

    @Autowired
    private final UserEntityToUserMapper userEntityToUserMapper = UserEntityToUserMapper.initialize();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsUserEntityByUsername(registerRequest.getUsername())) {
            throw new UserAlreadyExistException("The username is already used for another admin : " + registerRequest.getUsername());
        }

        final UserEntity userEntityToBeSave = registerRequestToUserEntityMapper.mapForSaving(registerRequest);

        userEntityToBeSave.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        UserEntity savedUserEntity = userRepository.save(userEntityToBeSave);

        return userEntityToUserMapper.map(savedUserEntity);

    }

}