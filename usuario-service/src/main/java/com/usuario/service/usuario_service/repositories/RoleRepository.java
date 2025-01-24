package com.usuario.service.usuario_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.usuario.service.usuario_service.entities.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByName(String name);
}

