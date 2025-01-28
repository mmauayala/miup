package com.usuario.service.usuario_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.usuario.service.usuario_service.model.user.entities.InvalidTokenEntity;

public interface InvalidTokenRepository extends CrudRepository<InvalidTokenEntity, String> {

    Optional<InvalidTokenEntity> findByTokenId(final String tokenId);

}
