package com.miup.sg.productos.product_service.filter;

import com.miup.sg.productos.product_service.client.UserServiceClient;
import com.miup.sg.productos.product_service.models.auth.Token;

import feign.FeignException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@SuppressWarnings("null")
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomBearerTokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    protected void doFilterInternal( @NonNull final HttpServletRequest httpServletRequest,
                                    @NonNull final HttpServletResponse httpServletResponse,
                                    @NonNull final FilterChain filterChain) throws ServletException, IOException {

        log.debug("CustomBearerTokenAuthenticationFilter: Request received for URI: {}", httpServletRequest.getRequestURI());

        final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if (Token.isBearerToken(authorizationHeader)) {
            final String jwt = Token.getJwt(authorizationHeader);

            try {
                userServiceClient.validateToken(jwt);
                log.debug("Token validation succeeded for request: {}", httpServletRequest.getRequestURI());

                final UsernamePasswordAuthenticationToken authentication = userServiceClient.getAuthentication(jwt);

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (FeignException e) {
                log.error("Token validation failed for request: {}", httpServletRequest.getRequestURI(), e);

                if (e instanceof FeignException.Unauthorized || e instanceof FeignException.Forbidden) {
                    httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                } else {
                    httpServletResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                }
                httpServletResponse.getWriter().write(e.getMessage());
            }
        } else {
            log.warn("Missing or invalid Authorization header for request: {}", httpServletRequest.getRequestURI());
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}