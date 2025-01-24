package com.usuario.service.usuario_service.services;

import java.util.List;
import java.util.Optional;

import com.usuario.service.usuario_service.entities.User;

public interface UserService {

    List<User> findAll();

    User save(User user);

    boolean existsByUsername(String username);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

}
