package com.usuario.service.usuario_service.model.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenInvalidateRequest {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

}