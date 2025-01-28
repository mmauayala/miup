package com.usuario.service.usuario_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.usuario.service.usuario_service.model.user.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, String> {

    boolean existsUserEntityByUsername(final String username);

    Optional<UserEntity> findByUsername(final String username);

}
