package com.authorization.auth_service.models.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotBlank(message = "No puede estar en blanco.")
    private String username;

    private String password;

    @NotBlank(message = "No puede estar en blanco.")
    private String role;

}