package com.authorization.auth_service.services;

import jakarta.validation.Valid;
import com.authorization.auth_service.models.common.response.CustomResponse;
import com.authorization.auth_service.models.auth.dto.request.TokenInvalidateRequest;
import org.springframework.web.bind.annotation.RequestBody;

public interface LogoutService {

    CustomResponse<Void> logout(@RequestBody @Valid final TokenInvalidateRequest tokenInvalidateRequest);

}
