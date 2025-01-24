package com.usuario.service.usuario_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.usuario.service.usuario_service.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);
 
    Optional<User> findByUsername(String username);

}
