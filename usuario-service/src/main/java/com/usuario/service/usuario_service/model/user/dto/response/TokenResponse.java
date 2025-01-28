package com.usuario.service.usuario_service.model.user.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {

    private String accessToken;
    private Long accessTokenExpiresAt;
    private String refreshToken;

}

