package com.usuario.service.usuario_service.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.usuario.service.usuario_service.model.user.Token;

import java.util.Map;
import java.util.Set;

public interface TokenService {

    Token generateToken(final Map<String, Object> claims);

    Token generateToken(final Map<String, Object> claims, final String refreshToken);

    UsernamePasswordAuthenticationToken getAuthentication(final String token);

    void verifyAndValidate(final String jwt);

    void verifyAndValidate(final Set<String> jwts);

    Jws<Claims> getClaims(final String jwt);

    Claims getPayload(final String jwt);

    String getId(final String jwt);

}