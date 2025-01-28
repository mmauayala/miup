package com.usuario.service.usuario_service.model.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.usuario.service.usuario_service.model.common.mapper.BaseMapper;
import com.usuario.service.usuario_service.model.user.User;
import com.usuario.service.usuario_service.model.user.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserToUserEntityMapper extends BaseMapper<User, UserEntity> {
    
    @Override
    UserEntity map(User source);

    static UserToUserEntityMapper initialize() {
        return Mappers.getMapper(UserToUserEntityMapper.class);
    }

}
