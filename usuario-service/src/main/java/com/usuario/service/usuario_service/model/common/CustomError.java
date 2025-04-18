package com.usuario.service.usuario_service.model.common;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class CustomError {

    @Builder.Default
    private final LocalDateTime time = LocalDateTime.now();

    private final HttpStatus httpStatus;

    private final String header;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String message;

    @Builder.Default
    private final Boolean isSuccess = false;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<CustomSubError> subErrors;

    @Getter
    @Builder
    public static class CustomSubError {

        private final String message;

        private final String field;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Object value;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final String type;

    }

    @Getter
    @RequiredArgsConstructor
    public enum Header {

        API_ERROR("API ERROR"),

        ALREADY_EXIST("ALREADY EXIST"),

        NOT_FOUND("NOT EXIST"),

        VALIDATION_ERROR("VALIDATION ERROR"),

        DATABASE_ERROR("DATABASE ERROR"),

        PROCESS_ERROR("PROCESS ERROR"),

        AUTH_ERROR("AUTH ERROR");


        private final String name;

    }

}