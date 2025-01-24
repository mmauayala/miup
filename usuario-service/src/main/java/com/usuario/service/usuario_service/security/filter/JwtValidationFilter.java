package com.usuario.service.usuario_service.security.filter;

import static com.usuario.service.usuario_service.security.TokenJwtConfig.CONTENT_TYPE;
import static com.usuario.service.usuario_service.security.TokenJwtConfig.HEADER_AUTHORIZATION;
import static com.usuario.service.usuario_service.security.TokenJwtConfig.PREFIX_TOKEN;
import static com.usuario.service.usuario_service.security.TokenJwtConfig.SECRET_KEY;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usuario.service.usuario_service.security.SimpleGrantedAuthorityJsonCreator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

                String header = request.getHeader(HEADER_AUTHORIZATION); // OBTENER TOKEN O CABECERA (header)

                if (header == null || !header.startsWith(PREFIX_TOKEN)){ // verificar que la cabecera sea distinta a null
                    
                    chain.doFilter(request, response);
                    return;
                }

                String token = header.replace(PREFIX_TOKEN, ""); // SUSTRAEMOS EL BEARER(PREFIXTOKEN) PARA QUEDARNOS UNICAMENTE CON EL TOKEN
                
                // VERIFICAR EL TOKEN
                try {
                    Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();// PARSEAR EL TOKEN, PASAMOS LA LLAVE YA GENERADA, 
                    // LO CONSTRUIMOS Y LE PASAMOS EL TOKEN
                    String usename = claims.getSubject();
                    Object authoritiesClaims = claims.get("authorities");

                    Collection<? extends GrantedAuthority> authorities = Arrays.asList( //convertir el json a un tipo object, lo hacemos una lista
                        new ObjectMapper().
                        addMixIn(SimpleGrantedAuthority.class, 
                            SimpleGrantedAuthorityJsonCreator.class)
                        .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class)
                        ); // objeto del tipo SimpleGrantedAu...

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usename, null, authorities);
                        SecurityContextHolder .getContext().setAuthentication(authenticationToken);
                        chain.doFilter(request, response);

                } catch (JwtException e) {
                    Map<String, String> body = new HashMap<>();
                    body.put("error", e.getMessage());
                    body.put("message", "El token de JWT es invalido!");

                     response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                     response.setStatus(HttpStatus.UNAUTHORIZED.value());
                     response.setContentType(CONTENT_TYPE);
                }
                



    }

    
}
