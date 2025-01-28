package com.miup.sg.productos.product_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.miup.sg.productos.product_service.config.FeignClientConfig;

@FeignClient(name = "usuario-service", path = "/v1/usuarios", configuration = FeignClientConfig.class)
public interface UserServiceClient {

    @PostMapping("/validate-token")
    void validateToken(@RequestParam String token);

    @GetMapping("/authenticate")
    UsernamePasswordAuthenticationToken getAuthentication(@RequestParam String token);

}