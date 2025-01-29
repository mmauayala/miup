package com.usuario.service.usuario_service.model.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomResponse<T> {

    @Builder.Default
    private final LocalDateTime time = LocalDateTime.now();

    private final HttpStatus httpStatus;

    private final Boolean isSuccess;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T response;

    public static final CustomResponse<Void> SUCCESS = CustomResponse.<Void>builder()
            .httpStatus(HttpStatus.OK)
            .isSuccess(true)
            .build();

    public static <T> CustomResponse<T> successOf(final T response) {
        return CustomResponse.<T>builder()
                .httpStatus(HttpStatus.OK)
                .isSuccess(true)
                .response(response)
                .build();
    }

}