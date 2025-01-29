package com.api.gateway.service.api_gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "usuario-service", url = "http://localhost:8090", path = "/v1/usuarios")
public interface UserServiceClient {

    @PostMapping("/validate-token")
    void validateToken(@RequestParam String token);

}