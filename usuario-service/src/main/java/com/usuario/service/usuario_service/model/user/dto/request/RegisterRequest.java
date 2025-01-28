package com.usuario.service.usuario_service.model.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @Size(min = 8)
    private String password;

    @NotBlank(message = "No puede estar en blanco.")
    private String username;

    @NotBlank(message = "No puede estar en blanco.")
    private String role;

}