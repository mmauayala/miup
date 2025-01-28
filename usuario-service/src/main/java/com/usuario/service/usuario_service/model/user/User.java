package com.usuario.service.usuario_service.model.user;

import com.usuario.service.usuario_service.model.common.BaseDomainModel;
import com.usuario.service.usuario_service.model.user.enums.UserStatus;
import com.usuario.service.usuario_service.model.user.enums.UserType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class User extends BaseDomainModel {

    private String id;
    private String username;
    private String password;
    private UserStatus userStatus;
    private UserType userType;
}