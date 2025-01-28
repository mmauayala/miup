package com.authorization.auth_service.models.auth;

import com.authorization.auth_service.models.auth.enums.UserStatus;
import com.authorization.auth_service.models.auth.enums.UserType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class User {

    private String id;
    private String username;
    private UserStatus userStatus;
    private UserType userType;
}
