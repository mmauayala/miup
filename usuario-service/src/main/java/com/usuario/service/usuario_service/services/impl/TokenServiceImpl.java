package com.usuario.service.usuario_service.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.usuario.service.usuario_service.config.TokenConfigurationParameter;
import com.usuario.service.usuario_service.model.user.Token;
import com.usuario.service.usuario_service.model.user.enums.TokenClaims;
import com.usuario.service.usuario_service.model.user.enums.TokenType;
import com.usuario.service.usuario_service.model.user.enums.UserType;
import com.usuario.service.usuario_service.services.InvalidTokenService;
import com.usuario.service.usuario_service.services.TokenService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenConfigurationParameter tokenConfigurationParameter;
    
    @Autowired
    private InvalidTokenService invalidTokenService;

    @Override
    public Token generateToken(final Map<String, Object> claims) {

        final long currentTimeMillis = System.currentTimeMillis();

        final Date tokenIssuedAt = new Date(currentTimeMillis);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getAccessTokenExpireMinute()
        );

        final String accessToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(tokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        final Date refreshTokenExpiresAt = DateUtils.addDays(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getRefreshTokenExpireDay()
        );

        final String refreshToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(tokenIssuedAt)
                .expiration(refreshTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claim(TokenClaims.USER_ID.getValue(), claims.get(TokenClaims.USER_ID.getValue()))
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();

    }

    @Override
    public Token generateToken(final Map<String, Object> claims, final String refreshToken) {
        final long currentTimeMillis = System.currentTimeMillis();

        final String refreshTokenId = this.getId(refreshToken);

        invalidTokenService.checkForInvalidityOfToken(refreshTokenId);

        final Date accessTokenIssuedAt = new Date(currentTimeMillis);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis),
                tokenConfigurationParameter.getAccessTokenExpireMinute()
        );

        final String accessToken = Jwts.builder()
                .header()
                .type(TokenType.BEARER.getValue())
                .and()
                .id(UUID.randomUUID().toString())
                .issuedAt(accessTokenIssuedAt)
                .expiration(accessTokenExpiresAt)
                .signWith(tokenConfigurationParameter.getPrivateKey())
                .claims(claims)
                .compact();

        return Token.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(final String token) {

        final Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(token);

        final JwsHeader jwsHeader = claimsJws.getHeader();
        final Claims payload = claimsJws.getPayload();

        final Jwt jwt = new org.springframework.security.oauth2.jwt.Jwt(
                token,
                payload.getIssuedAt().toInstant(),
                payload.getExpiration().toInstant(),
                Map.of(
                        TokenClaims.TYP.getValue(), jwsHeader.getType(),
                        TokenClaims.ALGORITHM.getValue(), jwsHeader.getAlgorithm()
                ),
                payload
        );

        final UserType userType = UserType.valueOf(payload.get(TokenClaims.USER_TYPE.getValue()).toString());

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userType.name()));

        return UsernamePasswordAuthenticationToken
                .authenticated(jwt, null, authorities);

    }

    @Override
    public void verifyAndValidate(final String jwt) {

        try {
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(jwt);

            Claims claims = claimsJws.getPayload();
            log.info("Token claims: {}", claims);

            if (claims.getExpiration().before(new Date())) {
                throw new JwtException("Token has expired");
            }

            log.info("Token is valid");

        } catch (ExpiredJwtException e) {
            log.error("Token has expired", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token has expired", e);
        } catch (JwtException e) {
            log.error("Invalid JWT token", e);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("Error validating token", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error validating token", e);
        }
    }

    @Override
    public void verifyAndValidate(final Set<String> jwts) {
        jwts.forEach(this::verifyAndValidate);
    }

    @Override
    public Jws<Claims> getClaims(final String jwt) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(jwt);

    }

    @Override
    public Claims getPayload(final String jwt) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    @Override
    public String getId(final String jwt) {
        return Jwts.parser()
                .verifyWith(tokenConfigurationParameter.getPublicKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getId();
    }

}