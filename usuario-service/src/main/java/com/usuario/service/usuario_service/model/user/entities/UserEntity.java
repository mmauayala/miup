package com.usuario.service.usuario_service.model.user.entities;
import java.util.HashMap;
import java.util.Map;

import com.usuario.service.usuario_service.model.common.entity.BaseEntity;
import com.usuario.service.usuario_service.model.user.enums.TokenClaims;
import com.usuario.service.usuario_service.model.user.enums.UserStatus;
import com.usuario.service.usuario_service.model.user.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "usuario_id")
    private String id;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @lombok.Builder.Default
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.ACTIVE;

    public Map<String, Object> getClaims() {

        final Map<String, Object> claims = new HashMap<>();

        claims.put(TokenClaims.USER_ID.getValue(), this.id);
        claims.put(TokenClaims.USER_TYPE.getValue(), this.userType);
        claims.put(TokenClaims.USER_STATUS.getValue(), this.userStatus);
        claims.put(TokenClaims.USER_USERNAME.getValue(), this.username);

        return claims;

    }

}
