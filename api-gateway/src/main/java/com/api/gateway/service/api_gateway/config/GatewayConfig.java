package com.api.gateway.service.api_gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.api.gateway.service.api_gateway.filter.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/v1/autenticacion/usuarios/register",
            "/v1/autenticacion/usuarios/login",
            "/v1/autenticacion/usuarios/refresh-token",
            "/v1/autenticacion/usuarios/logout"
    );

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product-service", r -> r.path("/v1/productos/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://product-service"))
                .route("auth-service", r -> r.path("/v1/autenticacion/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://auth-service"))
                .route("usuario-service", r -> r.path("/v1/usuarios/**")
                        .filters(f -> f.filter(jwtAuthFilter.apply(new JwtAuthenticationFilter.Config()
                                .setPublicEndpoints(PUBLIC_ENDPOINTS))))
                        .uri("lb://usuario-service"))
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173")); // Solo un origen
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}