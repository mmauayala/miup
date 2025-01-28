package com.usuario.service.usuario_service.model.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.usuario.service.usuario_service.model.common.mapper.BaseMapper;
import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.dto.response.TokenResponse;

@Mapper(componentModel = "spring")
public interface TokenToTokenResponseMapper extends BaseMapper<Token, TokenResponse> {

    @Override
    TokenResponse map(Token source);

    static TokenToTokenResponseMapper initialize() {
        return Mappers.getMapper(TokenToTokenResponseMapper.class);
    }

}