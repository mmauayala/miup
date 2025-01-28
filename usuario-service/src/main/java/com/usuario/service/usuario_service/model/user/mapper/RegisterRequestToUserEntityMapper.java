package com.usuario.service.usuario_service.model.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.usuario.service.usuario_service.model.common.mapper.BaseMapper;
import com.usuario.service.usuario_service.model.user.dto.request.RegisterRequest;
import com.usuario.service.usuario_service.model.user.entities.UserEntity;
import com.usuario.service.usuario_service.model.user.enums.UserType;

@Mapper(componentModel = "spring")
public interface RegisterRequestToUserEntityMapper extends BaseMapper<RegisterRequest, UserEntity> {

    @Named("mapForSaving")
    default UserEntity mapForSaving(RegisterRequest userRegisterRequest) {

        UserType userType = "admin".equalsIgnoreCase(userRegisterRequest.getRole()) ? UserType.ADMIN : UserType.USER;

        return UserEntity.builder()
                .username(userRegisterRequest.getUsername())
                .userType(userType)
                .build();
    }

    static RegisterRequestToUserEntityMapper initialize() {
        return Mappers.getMapper(RegisterRequestToUserEntityMapper.class);
    }

}